package com.softtech.android_structure.entities.account

import com.google.gson.annotations.SerializedName

data class LoginParameters (@SerializedName("username")val userName:String,@SerializedName("password") val password:String)
