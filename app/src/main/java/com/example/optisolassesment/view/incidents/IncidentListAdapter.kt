package com.example.optisolassesment.view.incidents

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.optisolassesment.R
import com.example.optisolassesment.databinding.IncidentListItemBinding
import com.example.optisolassesment.model.Incidents
import com.example.optisolassesment.model.Media
import com.example.optisolassesment.utils.DataUtil.incidentArrayList
import com.example.optisolassesment.utils.FragmentManagerUtil.replaceFragment
import com.example.optisolassesment.utils.getListData


class IncidentListAdapter(private val activity: FragmentActivity?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var isLoadingAdded = false
    private val ITEM = 0
    private val LOADING = 1

    fun setData(incidents: List<Incidents>) {
        incidentArrayList.addAll(incidents.getListData())
        notifyDataSetChanged()
    }


    class IncidentViewHolder(private val binding: IncidentListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            activity: FragmentActivity?,
            position: Int,
            incidents: Incidents
        ) {
            binding.root.setOnClickListener {
                activity?.let {
                    Log.d("TAG",it.toString())
                    val detailFragment =
                        IncidentDetailFragment()
                    val bundle = Bundle()
                    bundle.putInt("POSITION", position)
                    detailFragment.arguments = bundle
                    replaceFragment(it.supportFragmentManager, detailFragment)
                }
            }
            binding.incidents = incidents
            binding.executePendingBindings()
        }

    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1 && isLoadingAdded) LOADING else ITEM
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == ITEM) {
            val binding = IncidentListItemBinding.inflate(inflater)
            IncidentViewHolder(
                binding
            )
        } else {
            val view = inflater.inflate(R.layout.loading_view, parent, false)
            LoadingViewHolder(
                view
            )
        }
    }

    override fun getItemCount() = incidentArrayList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM -> (holder as IncidentViewHolder).bind(
                activity,
                position,
                incidentArrayList[position]
            )
            LOADING -> {
            }
        }

    }


    fun addLoadingFooter() {
        isLoadingAdded = true
        add(
            Incidents(
                0, "", "", "", 0, 0,
                "", "", "", "", Media("", "")
            )
        )
    }

    private fun add(incidents: Incidents) {
        incidentArrayList.add(incidents)
        notifyItemInserted(itemCount - 1)
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position = itemCount - 1
        incidentArrayList.removeAt(position)
        notifyItemRemoved(position)
    }
}