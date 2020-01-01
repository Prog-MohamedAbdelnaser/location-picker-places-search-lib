package com.softtech.android_structure.features.location.fragmentes

import android.util.Log
import android.view.View
import com.softartch_lib.locationpicker.LocationAddress
import com.google.android.gms.maps.MapView
import com.softartch_lib.locationpicker.LocationPickerFragmentWithSearchBar
import com.softtech.android_structure.R
import kotlinx.android.synthetic.main.fragment_location.*

class AddressFragmentWithSearchBar : LocationPickerFragmentWithSearchBar(){
    override fun mapViewResource(): MapView =mapView

    override fun layoutResource(): Int = R.layout.fragment_location

    override fun onViewInflated(parentView: View, childView: View) {
        super.onViewInflated(parentView, childView)
        //setSearchCountryFilter("")
    }

    override fun onGetLocationAddress(locationAddress: LocationAddress) {
        Log.i("onGetLocationAddress","${locationAddress.toString()}")
    }

}