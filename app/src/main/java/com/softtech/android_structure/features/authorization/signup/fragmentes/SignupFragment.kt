package com.softtech.android_structure.features.authorization.signup.fragmentes

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.softtech.android_structure.R
import com.softtech.android_structure.base.fragment.BaseFragment


class SignupFragment : BaseFragment() {
    override fun layoutResource(): Int =R.layout.fragment_signup
    override fun onViewInflated(parentView: View, childView: View) {
        super.onViewInflated(parentView, childView)

        initObservers()
        initEventHandler()

    }

    private fun initEventHandler() {

    }

    private fun initObservers() {

    }
}
