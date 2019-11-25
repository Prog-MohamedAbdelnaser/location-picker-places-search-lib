package com.softtech.android_structure.domain.entities

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@Parcelize
@IgnoreExtraProperties
data class ChatMessage(var senderId:String?=null, var text:String?=null, var created_at:String?=null,var type:Int?=null,var messageId:Long?=null,var sendStatus:Boolean=false,var readStatus:Boolean=false):Parcelable
