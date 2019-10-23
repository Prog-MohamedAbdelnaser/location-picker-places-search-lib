package com.softtech.android_structure.domain.entities.account

import com.google.gson.annotations.SerializedName
import com.softtech.android_structure.BuildConfig
import com.softtech.android_structure.data.constants.AppConstants.PLAT_FORM

data class LoginParameters(

	@SerializedName("password")
	val password: String,

	@SerializedName("phoneNumber")
	val phoneNumber: String,

	@SerializedName("deviceId")
	val deviceId: String,

	@SerializedName("version")
	val version: String? =  BuildConfig.VERSION_NAME,

	@SerializedName("platform")
	val platform: String? = PLAT_FORM
)