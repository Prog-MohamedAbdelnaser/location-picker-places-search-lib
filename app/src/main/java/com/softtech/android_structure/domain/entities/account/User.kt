package com.softtech.android_structure.domain.entities.account

import com.google.gson.annotations.SerializedName

// Created by mohamed abdelnaser on 7/4/19.
data class User (@SerializedName("id")var id :String?="",@SerializedName("name") var name:String?="")