package com.locationpicker.sample.features.location.fragmentes

import android.os.Bundle
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
import timber.log.Timber

class AddressFragmentWithSearchBar : LocationPickerFragmentWithSearchBar(){
    override fun mapViewResource(): MapView = mapView

    //todo set you api key here
    override fun setGoogleAPIKEY(): String = ""

    override fun layoutResource(): Int = R.layout.fragment_location

    override fun onGetLocationAddress(locationAddress: LocationAddress) {

        Timber.i("On get location Address ${locationAddress}")

    }

    override fun onViewCreated(parentView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(parentView, savedInstanceState)
        setSearchViewAutoComplete(searchViewAuto)
        setSearchLocalizationFilter("SA")

    }

}