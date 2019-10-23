package com.softtech.android_structure.features.authorization.verification.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.softtech.android_structure.R
import kotlinx.android.synthetic.main.fragment_forget_password.*

class VerificationActivity : AppCompatActivity() {

    companion object{
        val VERIFICATION_REQUEST_CODE: Int=1000
        fun navigateToVerificationScreen(activity: Activity){
            val intent= Intent(activity,VerificationActivity::class.java)
           activity. startActivityForResult(intent,VERIFICATION_REQUEST_CODE)
        }


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)
    }

    override fun onBackPressed() {
        finish()
    }
}
