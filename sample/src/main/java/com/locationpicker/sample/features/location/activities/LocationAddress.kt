package com.locationpicker.sample.features.location.activities

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.navigateUp
import com.google.android.gms.maps.MapView
import com.locationpicker.sample.R
import com.locationpicker.sample.base.activity.BaseActivity
import com.softartch_lib.locationpicker.LocationAddress
import com.softartch_lib.locationpicker.LocationPickerDialog
import kotlinx.android.synthetic.main.location_dialog.*

class LocationAddress: BaseActivity() {

    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        navController =  findNavController(R.id.fragmentNav)
/*
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val prev = supportFragmentManager.findFragmentByTag("dialog")
        if (prev != null) {
            fragmentTransaction.remove(prev)
        }
        fragmentTransaction.addToBackStack(null)

        val dialogFragment = LocationPicker() //here MyDialog is my custom dialog
        dialogFragment.show(supportFragmentManager, "dialog")*/
    }

    override fun onBackPressed() {
        navController.navigateUp()
    }
}