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

    //todo set you api key here
    override fun setGoogleAPIKEY(): String  = ""

}