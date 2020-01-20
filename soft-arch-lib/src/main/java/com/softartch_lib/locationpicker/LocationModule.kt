package com.softartch_lib.locationpicker

import android.location.Geocoder
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import java.util.*

val locationModule= module {

    val GOOGLE_API_KEY ="AIzaSyBa3yCy4tWnGrzJ04A-kov18BBkUeuCj6s"

    factory { Geocoder(androidApplication(), Locale.getDefault()) }

    factory {
        LocationAddressRepository(get(), "service_not_available", "location_not_valid", "location_not_valid")
    }

    factory { LocationAddressUseCase(get()) }

    single { AutocompleteSessionToken.newInstance() }

    viewModel { LocationPickerViewModel(get(),get(),androidApplication()) }
}