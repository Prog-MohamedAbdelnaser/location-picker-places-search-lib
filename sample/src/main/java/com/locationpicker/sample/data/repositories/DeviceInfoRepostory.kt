package com.locationpicker.sample.data.repositories

import android.content.Context
import android.os.Build
import android.provider.Settings
import com.locationpicker.sample.data.repositories.RepositoriesConstants.KEY_DEVICE_INFO
import com.locationpicker.sample.data.sources.local.AppPreference
import timber.log.Timber


class DeviceInfoRepostory(private val appPreference: AppPreference, private val context: Context) {


    private fun setDeviceID(deviceId: String) { appPreference.putStringWithCommit(KEY_DEVICE_INFO, deviceId) }

    fun getDeviceID(): String? {

     val deviceId=  appPreference.getString(KEY_DEVICE_INFO,null)

        if (deviceId==null) return getDeviceIdFromSetting() else return deviceId
    }

   private fun getDeviceIdFromSetting(): String {
       val androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
       val deviceId = Build.SERIAL + "#" + androidId
       Timber.i("deviceId id ${deviceId}")
           setDeviceID(deviceId)

        return deviceId
    }

    private fun isMarshmallowOrLater(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

}