package com.softartch_lib.locationpicker

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize data class PlaceAutoComplete(val placeId:String, val placeName:String, val placeFullDescription:String):Parcelable