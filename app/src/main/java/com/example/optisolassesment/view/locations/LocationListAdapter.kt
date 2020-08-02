package com.example.optisolassesment.view.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.optisolassesment.databinding.LocationListItemBinding
import com.example.optisolassesment.model.LocationFeature
import com.example.optisolassesment.utils.FragmentManagerUtil

class LocationListAdapter(private val activity: FragmentActivity?) :
    RecyclerView.Adapter<LocationListAdapter.LocationViewHolder>() {

    private var locationList = ArrayList<LocationFeature>()
    fun setData(locationFeatureList: List<LocationFeature>) {
        locationList = locationFeatureList as ArrayList<LocationFeature>
        notifyDataSetChanged()
    }

    class LocationViewHolder(private val binding: LocationListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            activity: FragmentActivity?,
            locationFeature: LocationFeature
        ) {
            binding.root.setOnClickListener {
                activity?.let {
                    val mapsFragment = MapsFragment()
                    val bundle = Bundle()
                    bundle.putParcelable("LOCATION_ITEM", locationFeature)
                    mapsFragment.arguments = bundle
                    FragmentManagerUtil.replaceFragment(
                        it.supportFragmentManager,
                        mapsFragment
                    )
                }
            }
            binding.locations = locationFeature
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LocationListItemBinding.inflate(inflater)
        return LocationViewHolder(
            binding
        )
    }

    override fun getItemCount() = locationList.size

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) =
        holder.bind(activity, locationList[position])
}