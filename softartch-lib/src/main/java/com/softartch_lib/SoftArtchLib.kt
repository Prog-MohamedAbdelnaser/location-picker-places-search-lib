package com.softartch_lib

import com.softartch_lib.locationpicker.locationModule
import org.koin.standalone.StandAloneContext.loadKoinModules

object SoftArtchLib {

    fun init() = loadKoinModules(
        locationModule
    )

}