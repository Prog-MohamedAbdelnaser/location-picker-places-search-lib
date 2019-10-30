package com.softtech.android_structure.features.temp.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.softtech.android_structure.R

class LocationActivity : AppCompatActivity() {

    companion object{
        fun startLocationPicker(activity: Activity){
            activity.apply {
                val intent=Intent(this,LocationActivity::class.java)
                startActivity(intent)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
    }
}
