package com.softtech.android_structure.entities.account

import com.google.gson.annotations.SerializedName
import com.softtech.android_structure.BuildConfig
import com.softtech.android_structure.data.constants.AppConstants.PLAT_FORM

data class RegisterParams(

	@SerializedName("fname")
	val fname: String? = null,

	@SerializedName("password")
	val password: String? = null,

	@SerializedName("phoneNumber")
	val phoneNumber: String? = null,

	@SerializedName("addressLatitude")
    var addressLatitude: String? = null,

	@SerializedName("addressLongitude")
	var addressLongitude: String? = null,

	@SerializedName("address")
	var address: String? = null,

	@SerializedName("deviceId")
	val deviceId: String? = null,

	@SerializedName("version")
	val version: String? = BuildConfig.VERSION_NAME,

	@SerializedName("email")
	val email: String? = null,
	@SerializedName("isVerifiedEmail")
	val isVerifiedEmail: Boolean? = false,

	@SerializedName("platform")
	val platform: String? = PLAT_FORM
)