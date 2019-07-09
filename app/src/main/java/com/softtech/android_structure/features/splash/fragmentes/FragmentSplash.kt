package com.softtech.android_structure.features.splash.fragmentes

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.softtech.android_structure.R
import com.softtech.android_structure.base.fragment.BaseFragment
import com.softtech.android_structure.features.authorization.AuthorizationActivity
import com.softtech.android_structure.features.home.activities.HomeActivity
import com.softtech.android_structure.features.splash.vm.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

// by moahmed abdelnaser4-7-2019

class FragmentSplash : BaseFragment() {
    val handler=Handler(Looper.getMainLooper())
    val splashViewModel:SplashViewModel by viewModel()
    override fun layoutResource(): Int =R.layout.fragment_splash

    override fun onViewInflated(parentView: View, childView: View) {
        super.onViewInflated(parentView, childView)

        Timber.i("FragmentSplash")
        splashViewModel.apply {
            isLogedInLiveData.observe(this@FragmentSplash, Observer {
                Timber.i("isLogedInLiveData $it")
                handler.postDelayed({
                    if (it) moveToHome()
                    else moveToLogin()
                },3000)

            })
        }

    }

    fun moveToLogin(){
        val  intent=Intent(requireContext(),AuthorizationActivity::class.java)
        requireActivity().overridePendingTransition(0,0)
        startActivity(intent)
        requireActivity().overridePendingTransition(0,0)
        requireActivity().finish()
    }

    fun moveToHome(){
        val  intent=Intent(requireContext(),HomeActivity::class.java)
        requireActivity().overridePendingTransition(0,0)
        startActivity(intent)
        requireActivity().overridePendingTransition(0,0)
        requireActivity().finish()
    }
}
