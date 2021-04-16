package com.locationpicker.sample.features.location.fragmentes

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.WindowManager
import com.google.android.gms.maps.MapView
import com.locationpicker.sample.R
import com.softartch_lib.locationpicker.LocationAddress
import com.softartch_lib.locationpicker.LocationPickerDialog
import kotlinx.android.synthetic.main.location_dialog.*

class LocationPicker : LocationPickerDialog() {
    override fun layoutResource(): Int = R.layout.location_dialog

    override fun mapViewResource(): MapView = mapView

    override fun setGoogleAPIKEY(): String = "AIzaSyAU6Pf-8uWRgWcDyfaCdKgw-uINqGIsi3E"

    override fun onStart() {
        super.onStart()
        requireDialog().window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

    }


    override fun onViewInflated(parentView: View, childView: View) {
        super.onViewInflated(parentView, childView)
        requireDialog().window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        btnSave.setOnClickListener {
            requireActivity().onBackPressed()
            dismiss()
        }
    }

    override fun onGetLocationAddress(locationAddress: LocationAddress) {
        super.onGetLocationAddress(locationAddress)
    }
}
