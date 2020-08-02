package com.example.optisolassesment.view.incidents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.optisolassesment.R
import com.example.optisolassesment.model.Incidents
import com.example.optisolassesment.utils.DataUtil.incidentArrayList
import com.example.optisolassesment.utils.bindDate
import com.example.optisolassesment.utils.bindImageUrl
import com.example.optisolassesment.utils.bindText
import com.example.optisolassesment.view.MainActivity
import kotlinx.android.synthetic.main.fragment_incident_detail.*

class IncidentDetailFragment : Fragment() {
    private lateinit var selectedIncident: Incidents
    private var listPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        bundle?.let {
            listPosition = it.getInt("POSITION")
        }
        selectedIncident = incidentArrayList[listPosition]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_incident_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpActionBar()

        action_image.bindImageUrl(selectedIncident.media.image_url)
        txt_address.bindText(selectedIncident.address)
        txt_title.bindText(selectedIncident.title)
        txt_desc.bindText(selectedIncident.description)
        txt_date.bindDate(selectedIncident.occurred_at)

    }


    private fun setUpActionBar() {
        if ((activity as MainActivity).supportActionBar != null) {
            val actionBar = (activity as MainActivity).supportActionBar
            selectedIncident.let {
                actionBar?.title = selectedIncident.title
            }
            actionBar!!.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeButtonEnabled(true)
        }
    }

}