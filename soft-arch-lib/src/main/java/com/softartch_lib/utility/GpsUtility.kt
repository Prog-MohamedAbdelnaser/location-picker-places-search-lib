package com.locationpicker.sample.base.utility
import com.google.android.gms.location.LocationRequest

object GpsUtility {

    const val LOCATION_SERVICE_SETTING_REQUEST_CODE=1000
    var locationRequest:LocationRequest?=null


    fun createLocationRequest() =
        LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setFastestInterval(5000)
            .setInterval(8000)
            .setSmallestDisplacement(5f)


sealed class Mode{
    object HighAccurcay:Mode()
    object Enable:Mode()
    object Disabled:Mode()

}

}