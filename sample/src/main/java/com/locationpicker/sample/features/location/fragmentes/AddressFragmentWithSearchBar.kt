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

class AddressFragmentWithSearchBar : LocationPickerFragmentWithSearchBar(){



    override fun mapViewResource(): MapView =mapView

    override fun layoutResource(): Int = R.layout.fragment_location


    override fun clickPickedPlace(locationName:String) {
        searchViewAuto.getRecycleViewResults()!!.hide()
        progressBar.hide()
        tvSearchPlaceHolderMessage.hide()
        //searchViewAuto.getSearchView()?.setQuery("", true);
        searchViewAuto.getSearchView()?.clearFocus()

    }

    override fun onAutoCompleteSearchStart() {
        searchViewAuto.getRecycleViewResults()!!.show()
        progressBar.show()

    }

    override fun onAutoCompleteSearchFinised(resultIsNotEmpty: Boolean) {
        searchViewAuto.getRecycleViewResults()!!.show()
        progressBar.hide()
        if (resultIsNotEmpty.not()){
            searchViewAuto.getRecycleViewResults()!!.hide()
            tvSearchPlaceHolderMessage.show()
        }

    }

    override fun onViewInflated(parentView: View, childView: View) {
        super.onViewInflated(parentView, childView)

       //to fillter auto complete  search result
        // setSearchLocalizationFilter(SAUDIA_FILTER)

        initRecycleView()

        searchViewAuto.getSearchView()?.setOnQueryTextListener(object :SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty().not()) {
                    searchQueryListener(newText.toString())
                }else{
                    searchViewAuto.getRecycleViewResults()!!.hide()
                }
                return false
            }
        })
        //setSearchCountryFilter("")
    }

    private fun initRecycleView() {
        val adapter=getAutoCompleteSearchResultAdapter()
        searchViewAuto.getRecycleViewResults()?.layoutManager = LinearLayoutManager(requireContext())
        searchViewAuto.getRecycleViewResults()?.adapter = adapter
    }
    override fun onGetLocationAddress(locationAddress: LocationAddress) {
        Log.i("onGetLocationAddress","${locationAddress.toString()}")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }



}