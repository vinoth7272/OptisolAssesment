package com.example.optisolassesment.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.optisolassesment.R
import kotlinx.android.synthetic.main.tab_fragment.*

class TabFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tab_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()

        if ((activity as MainActivity).supportActionBar != null) {
            val actionBar = (activity as MainActivity).supportActionBar
            actionBar?.title = "Theft Incident"
            actionBar?.setDisplayHomeAsUpEnabled(false)
        }
        val fragmentPagerAdapter = TabsPagerAdapter(childFragmentManager)
        view_pager.adapter = fragmentPagerAdapter
        tabLayout.setupWithViewPager(view_pager)
    }
}