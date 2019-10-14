package com.softtech.android_structure.entities.account

import com.google.gson.annotations.SerializedName
import com.softtech.android_structure.BuildConfig
import com.softtech.android_structure.data.constants.AppConstants.PLAT_FORM

data class LoginParameters(

	@SerializedName("password")
	val password: String? = null,

	@SerializedName("phoneNumber")
	val phoneNumber: String? = null,

	@SerializedName("deviceId")
	val deviceId: String? = null,

	@SerializedName("version")
	val version: String? =  BuildConfig.VERSION_NAME,

	@SerializedName("platform")
	val platform: String? = PLAT_FORM
)