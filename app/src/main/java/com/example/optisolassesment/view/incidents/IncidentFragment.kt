package com.example.optisolassesment.view.incidents

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.optisolassesment.R
import com.example.optisolassesment.model.Incidents
import com.example.optisolassesment.network.ApiHelperImpl
import com.example.optisolassesment.network.ApiService
import com.example.optisolassesment.network.Status
import com.example.optisolassesment.utils.DataUtil.incidentArrayList
import com.example.optisolassesment.utils.PaginationScrollListener
import com.example.optisolassesment.utils.isNetworkConnected
import com.example.optisolassesment.utils.showShortSnack
import com.example.optisolassesment.viewmodel.DataRepository
import com.example.optisolassesment.viewmodel.SharedViewModel
import com.example.optisolassesment.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_incident.*
import org.koin.android.ext.android.inject

class IncidentFragment : Fragment() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private val apiService: ApiService by inject()
    private val sharedViewModel: SharedViewModel by activityViewModels {
        ViewModelFactory(DataRepository(ApiHelperImpl(apiService)))
    }
    private lateinit var incidentListAdapter: IncidentListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        incidentListAdapter =
            IncidentListAdapter(
                activity
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_incident, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setScrollListener()
        loadData()
    }


    private fun loadData() {
        activity?.let {
            if (it.isNetworkConnected()) {
                sharedViewModel.fetchIncidentData()
            } else {
                progressBar.visibility = View.GONE
                parent.showShortSnack(it.getString(R.string.network_error))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        loadProgressBar()
        loadObserverData()
    }

    private fun setAdapter() {
        linearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recycler_view.layoutManager = linearLayoutManager
        recycler_view.adapter = incidentListAdapter
    }

    private fun loadObserverData() {
        sharedViewModel.incidentResponseLiveData.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> loadSuccess(it.data?.incidents)
                Status.ERROR -> loadError()
                Status.LOADING -> loadProgressBar()
            }
        })
    }

    private fun setScrollListener() {

        recycler_view.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
            override fun loadMoreItems() {
                loadData()
            }
        })
    }

    private fun loadError() {
        parent.showShortSnack("No Data Found")
        recycler_view.visibility = View.GONE
        progressBar.visibility = View.GONE
    }

    private fun loadProgressBar() {
        if (sharedViewModel.isFirstPage && incidentArrayList.isEmpty()) {
            recycler_view.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun loadSuccess(incidents: List<Incidents>?) {

        if (sharedViewModel.isFirstPage) {
            incidents?.let {
                incidentListAdapter.setData(it)
            }
            incidentListAdapter.addLoadingFooter()
            sharedViewModel.isFirstPage = false
            recycler_view.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        } else {
            incidentListAdapter.removeLoadingFooter()
            incidents?.let {
                incidentListAdapter.setData(it)
            }
            incidentListAdapter.addLoadingFooter()
        }

    }
}