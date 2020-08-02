package com.example.optisolassesment.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.optisolassesment.view.incidents.IncidentFragment
import com.example.optisolassesment.view.locations.LocationFragment

class TabsPagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> IncidentFragment()
            else -> LocationFragment()
        }
    }

    override fun getCount() = 2
    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Incidents"
            else -> "Location"
        }
    }
}