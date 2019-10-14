package com.softtech.android_structure.features.authorization.verification.fragmentes

import android.app.Activity
import android.content.Intent
import android.view.View
import com.softtech.android_structure.R
import com.softtech.android_structure.base.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_verification.*

class VerificationFragment: BaseFragment() {
    override fun layoutResource(): Int = R.layout.fragment_verification
    override fun onViewInflated(parentView: View, childView: View) {
        super.onViewInflated(parentView, childView)
        initEventHandler()
    }

    private fun initEventHandler() {
        btnNext.setOnClickListener {
            setActivityResult()
        }
    }

    private fun setActivityResult() {
        val intent= Intent()
        requireActivity().setResult(Activity.RESULT_OK,intent)
        requireActivity().finish()
    }
}