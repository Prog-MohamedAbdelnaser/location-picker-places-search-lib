package com.locationpicker.sample.features.location.fragmentes

import android.os.Bundle
import android.view.View
import com.google.android.libraries.places.api.model.Place
import com.locationpicker.sample.R
import com.locationpicker.sample.base.fragment.BaseFragment
import com.softartch_lib.component.widget.AutoCompletePlacesEditText
import kotlinx.android.synthetic.main.fragment_location_custom_searchview.*

class AddressFragmentWithCustomSearchComponent : BaseFragment(),
    AutoCompletePlacesEditText.AutoCompleteSearchViewListener {

    override fun layoutResource(): Int = R.layout.fragment_location_custom_searchview

    override fun onViewCreated(parentView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(parentView, savedInstanceState)
        searchViewAuto.initComponent(apiKey =getString(R.string.google_maps_key),editTextLayout = R.layout.search_edit_text,editTextID = R.id.editText)
            .setAutoCompleteSearchViewListener(this)
    }

    override fun onSearchResult(place: Place) {
        searchViewAuto.setText(place.address.toString())
    }

}