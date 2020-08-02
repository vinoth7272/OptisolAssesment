package com.example.optisolassesment.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.optisolassesment.R


object FragmentManagerUtil {
    fun replaceFragment(
        fragmentManager: FragmentManager,
        fragment: Fragment
    ) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.addToBackStack(fragment.tag)
        fragmentTransaction.commit()
    }
}
