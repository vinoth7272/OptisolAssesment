package com.example.optisolassesment.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.optisolassesment.R
import com.example.optisolassesment.utils.FragmentManagerUtil.replaceFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Theft Incident"
        supportActionBar?.setHomeButtonEnabled(true)
        replaceFragment(supportFragmentManager, TabFragment())

    }

    override fun onSupportNavigateUp(): Boolean {
        super.onSupportNavigateUp()
        onBackPressed()
        return true
    }
    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}