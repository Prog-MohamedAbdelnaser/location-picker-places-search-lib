package com.softtech.android_structure.features.location.fragmentes

import android.Manifest
import android.util.Log
import android.view.View
import com.softartch_lib.locationpicker.LocationAddress
import com.google.android.gms.maps.MapView
import com.softartch_lib.locationpicker.LocationPickerFragment
import com.softartch_lib.locationpicker.LocationPickerFragment2
import com.softtech.android_structure.R
import com.softtech.android_structure.base.utility.GpsUtility
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.fragment_location.*
import timber.log.Timber

class AddressFragment : LocationPickerFragment(){
    override fun mapViewResource(): MapView =mapView

    override fun layoutResource(): Int = R.layout.fragment_location

    override fun onViewInflated(parentView: View, childView: View) {
        super.onViewInflated(parentView, childView)
        //setSearchCountryFilter("")
    }

    override fun onGetLocationAddress(locationAddress: LocationAddress) {
        super.onGetLocationAddress(locationAddress)
    }

}