package com.softtech.android_structure.domain.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(var id:String?=null,var name:String?=null,var email:String?=null,var imageUrl:String?=null):Parcelable