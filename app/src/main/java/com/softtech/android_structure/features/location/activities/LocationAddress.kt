package com.softtech.android_structure.features.location.activities

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import com.softtech.android_structure.R
import com.softtech.android_structure.base.activity.BaseActivity

class LocationAddress: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
    }
}