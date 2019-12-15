package com.softtech.android_structure.base.utility
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.util.Log
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.tasks.Task
import com.softartch_lib.exceptions.LocationServiceRequestException
import com.softartch_lib.locationpicker.LocationPickerFragment
import com.softartch_lib.utility.EnableLocationServiceSetting
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.functions.Function
import timber.log.Timber

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