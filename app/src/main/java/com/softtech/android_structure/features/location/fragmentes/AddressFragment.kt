package com.softtech.android_structure.features.location.fragmentes

import android.view.View
import com.example.softartch_lib.locationpicker.LocationAddress
import com.google.android.gms.maps.MapView
import com.rent.client.features.location.fragmentes.LocationPickerFragment
import com.softtech.android_structure.R
import kotlinx.android.synthetic.main.fragment_location.*

class AddressFragment :LocationPickerFragment(){
    override fun mapViewResource(): MapView =mapView

    override fun layoutResource(): Int = R.layout.fragment_location

    override fun onViewInflated(parentView: View, childView: View) {
        super.onViewInflated(parentView, childView)
    }

    override fun onGetLocationAddress(locationAddress: LocationAddress) {
        super.onGetLocationAddress(locationAddress)
    }

}