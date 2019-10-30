package com.softtech.android_structure.features.temp.di

import android.location.Geocoder
import com.softtech.android_structure.R
import com.softtech.android_structure.data.repositories.LocationAddressRepository
import com.softtech.android_structure.data.sources.local.AppPreferenceConstants.DEFAULT_LOCALE
import com.softtech.android_structure.domain.usecases.LocationAddressUseCase
import com.softtech.android_structure.features.temp.vm.LocationViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val locationModule= module {

    factory { Geocoder(androidApplication(), get(DEFAULT_LOCALE)) }

    factory {
        LocationAddressRepository(
            get(),
            androidContext().getString(R.string.service_not_available),
            androidContext().getString(R.string.location_not_valid),
            androidContext().getString(R.string.no_address_found)
        )
    }

    factory { LocationAddressUseCase(get()) }

    viewModel { LocationViewModel(get()) }
}