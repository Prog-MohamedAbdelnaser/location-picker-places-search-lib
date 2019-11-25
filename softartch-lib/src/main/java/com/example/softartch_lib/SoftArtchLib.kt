package com.example.softartch_lib

import com.rent.client.features.location.di.locationModule
import org.koin.android.ext.android.startKoin
import org.koin.standalone.StandAloneContext.loadKoinModules
import org.koin.standalone.StandAloneContext.startKoin

object SoftArtchLib {

    fun init() = loadKoinModules(
        locationModule
    )

}