package com.softartch_lib.locationpicker

import android.location.Geocoder
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import java.util.*

val locationModule= module {



    factory { Geocoder(androidApplication(), Locale.getDefault()) }

    factory {
        LocationAddressRepository(get(), "service_not_available", "location_not_valid", "location_not_valid")
    }

    factory { LocationAddressUseCase(get()) }

    viewModel { LocationPickerViewModel(get()) }
}