package com.softtech.android_structure.base.utility
import android.content.Context
import com.google.android.gms.location.LocationRequest
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.functions.Function

object GpsUtility {


sealed class Mode{
    object HighAccurcay:Mode()
    object Enable:Mode()
    object Disabled:Mode()

}

}