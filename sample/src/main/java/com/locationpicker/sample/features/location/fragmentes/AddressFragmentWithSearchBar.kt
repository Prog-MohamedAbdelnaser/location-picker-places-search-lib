package com.locationpicker.sample.features.location.fragmentes

import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.softartch_lib.locationpicker.LocationAddress
import com.google.android.gms.maps.MapView
import com.softartch_lib.component.extension.hide
import com.softartch_lib.component.extension.show
import com.softartch_lib.locationpicker.LocationPickerFragmentWithSearchBar
import com.locationpicker.sample.R
import kotlinx.android.synthetic.main.fragment_location.*
import kotlinx.android.synthetic.main.fragment_location.btnSave
import kotlinx.android.synthetic.main.fragment_location.mapView
import kotlinx.android.synthetic.main.fragment_location.searchViewAuto
import kotlinx.android.synthetic.main.fragment_location_search_controller.*

class AddressFragmentWithSearchBar : LocationPickerFragmentWithSearchBar(){

    override fun setGoogleAPIKEY(): String =getString(R.string.google_maps_key)

    override fun mapViewResource(): MapView =mapView

    override fun layoutResource(): Int = R.layout.fragment_location

    override fun onViewInflated(parentView: View, childView: View) {
        super.onViewInflated(parentView, childView)

        //to fillter auto complete  search result



        // to initialize map location pin
        setMapPickLoctionIcon(R.drawable.ic_location)
        setSearchViewAutoComplete(searchViewAuto)
        btnSave.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }

    override fun onGetLocationAddress(locationAddress: LocationAddress) {

        // todo handle as you need the pick location result or location selected from search

        Log.i("onGetLocationAddress","$locationAddress")
    }



}