package com.softtech.android_structure.base.extension

import android.net.ConnectivityManager
import com.softtech.android_structure.application.ApplicationClass
import com.softtech.android_structure.application.MainModel

fun MainModel.isInternetConnected(): Boolean {

    val connectivityManager =
        ApplicationClass.appContext.getSystemService(android.content.Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected
}