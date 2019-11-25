package com.softtech.android_structure.domain.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Room(var roomId:String?=null,var friend:User?=null,var lastMessage:ChatMessage?=null):Parcelable