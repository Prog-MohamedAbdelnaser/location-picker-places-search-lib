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
import com.google.android.libraries.places.api.model.Place
import com.softartch_lib.component.extension.hide
import com.softartch_lib.component.extension.show
import com.softartch_lib.locationpicker.LocationPickerFragmentWithSearchBar
import com.locationpicker.sample.R
import com.softartch_lib.component.widget.AutoCompletePlacesController
import kotlinx.android.synthetic.main.fragment_location_search_controller.*
import kotlinx.android.synthetic.main.fragment_location_search_controller.btnSave
import kotlinx.android.synthetic.main.fragment_location_search_controller.mapView
import kotlinx.android.synthetic.main.location_dialog.*
import kotlinx.android.synthetic.main.search_edit_text.*

class AddressFragmentWithSearchContoller : LocationPickerFragmentWithSearchBar(),
    AutoCompletePlacesController.AutoCompletePlacesControllerListener {

    override fun mapViewResource(): MapView = mapView

    //todo set you api key here
    override fun setGoogleAPIKEY(): String = ""

    override fun layoutResource(): Int = R.layout.fragment_location_search_controller

    override fun onViewCreated(parentView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(parentView, savedInstanceState)
        AutoCompletePlacesController(requireContext()).
        initComponent(apiKey = getString(R.string.google_maps_key),recyclerView = recyclerView,editText = searchViewAuto)
            .setAutoCompletePlacesControllerListener(this)
    }

    override fun onSearchNoResult() {
        TODO("Not yet implemented")
    }

    override fun onFailure(exception: Throwable) {
        TODO("Not yet implemented")
    }

    override fun onSearchStart() {
        TODO("Not yet implemented")
    }

    override fun onSearchFinished() {
        TODO("Not yet implemented")
    }

    override fun onSearchResult(place: Place) {
        TODO("Not yet implemented")
    }

}