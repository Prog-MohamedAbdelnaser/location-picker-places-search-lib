package com.softtech.android_structure.base.utility
import android.content.Context
import com.google.android.gms.location.LocationRequest
import com.softartch_lib.utility.EnableLocationServiceSetting
import io.reactivex.Single

object GpsUtility {

    const val LOCATION_SERVICE_SETTING_REQUEST_CODE=1000
    var locationRequest:LocationRequest?=null

    fun requsetEnableLocationServiceSetting(context: Context): Single<Boolean> {
        return EnableLocationServiceSetting.checkLocationServiceSetting(context, createLocationRequest())
    }
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