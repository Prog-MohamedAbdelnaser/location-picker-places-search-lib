package com.softtech.android_structure.base.utility
import android.content.Context
import android.content.IntentSender
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.tasks.Task
import io.reactivex.Single
import io.reactivex.SingleSource
import timber.log.Timber
import io.reactivex.functions.Function

object GpsUtility {
    fun requsetEnableLocationServiceSetting(context: Context): Single<Boolean> {
        return EnableLocationServiceSetting.checkLocationServiceSetting(context, createLocationRequest())
    }
     fun createLocationRequest() =
        LocationRequest()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setFastestInterval(5000)
            .setInterval(8000)
            .setSmallestDisplacement(5f)




     fun requestLocationServiceSettingFunction(context: Context) =
        Function<Boolean, SingleSource<Boolean>> { granted ->
            if (granted) {
                requsetEnableLocationServiceSetting(context)
            } else {
                Single.error(IllegalArgumentException("Permissions must be granted first"))
            }
        }

sealed class Mode{
    object HighAccurcay:Mode()
    object Enable:Mode()
    object Disabled:Mode()

}

}