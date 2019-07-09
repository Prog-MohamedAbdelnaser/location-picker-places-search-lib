package com.softtech.android_structure.features.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.softtech.android_structure.R
import com.softtech.android_structure.base.activity.BaseActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
