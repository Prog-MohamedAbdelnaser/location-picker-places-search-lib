package com.softartch_lib.component.dialogs

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import com.softartch_lib.R
import kotlinx.android.synthetic.main.loading.*

class CustomeProgressDialog(val activity: Activity) : Dialog(activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.loading)
        setCancelable(false)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        loadingView.background=ColorDrawable(Color.TRANSPARENT)
        loadingView.visibility= View.VISIBLE
    }

}