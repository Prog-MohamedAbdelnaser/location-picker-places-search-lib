package com.softtech.android_structure.features.authorization

import android.bluetooth.le.ScanCallback
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.softtech.android_structure.R
import com.softtech.android_structure.base.activity.BaseActivity
import com.sunmi.electronicscaleservice.ScaleCallback
import com.sunmi.scalelibrary.ScaleManager

class AuthorizationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)

    }
}
