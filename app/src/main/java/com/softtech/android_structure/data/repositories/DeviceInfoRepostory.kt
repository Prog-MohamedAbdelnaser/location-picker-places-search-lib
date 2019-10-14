package com.softtech.android_structure.data.repositories

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import androidx.core.app.ActivityCompat
import timber.log.Timber
import android.hardware.usb.UsbDevice.getDeviceId
import com.softtech.android_structure.data.repositories.RepositoriesConstants.KEY_DEVICE_INFO
import com.softtech.android_structure.data.sources.local.AppPreference
import java.util.*


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