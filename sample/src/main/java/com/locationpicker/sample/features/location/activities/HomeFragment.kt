package com.locationpicker.sample.features.location.activities

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.MapView
import com.locationpicker.sample.R
import com.locationpicker.sample.features.location.fragmentes.LocationPicker
import com.softartch_lib.component.fragment.BaseFragment
import com.softartch_lib.locationpicker.LocationAddress
import com.softartch_lib.locationpicker.LocationPickerDialog
import kotlinx.android.synthetic.main.fragment_home2.*
import kotlinx.android.synthetic.main.location_dialog.*

class HomeFragment: BaseFragment(){
    override fun layoutResource(): Int = R.layout.fragment_home2

    override fun onViewInflated(parentView: View, childView: View) {
        super.onViewInflated(parentView, childView)

        tvMapFragmentWithPlacesSearch.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_locationPickerFragmentWithSearchBar)
        }

        tvLocationPickerDialog.setOnClickListener {

            LocationPicker().show(requireFragmentManager(),"TAG")
        }

        tvMapFragmentWithCustomPlacesSearchController.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addressFragmentWithSearchContoller)

        }

        tvMapFragmentWithCustomPlacesSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addressFragmentWithCustomSearchComponent)

        }
    }

}
