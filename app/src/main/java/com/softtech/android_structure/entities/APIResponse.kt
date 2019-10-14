package com.softtech.android_structure.entities

import com.google.gson.annotations.SerializedName
import com.softtech.android_structure.entities.EntitiesConstants.CODE
import com.softtech.android_structure.entities.EntitiesConstants.HTTP_STATUS
import com.softtech.android_structure.entities.EntitiesConstants.MESSAGE
import com.softtech.android_structure.entities.EntitiesConstants.PAYLOAD
import com.softtech.android_structure.entities.EntitiesConstants.STATUS

private const val SUCCESS_STATUS = "success"

data class APIResponse<P>(
    @SerializedName(STATUS) var status: String,
    @SerializedName(CODE) var code: String,
    @SerializedName(MESSAGE) var message: String,
    @SerializedName(PAYLOAD) var payload: P?) {

    fun isSuccessful(): Boolean {
        return status == SUCCESS_STATUS
    }

}