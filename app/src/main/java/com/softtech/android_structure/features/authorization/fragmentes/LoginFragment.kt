package com.softtech.android_structure.features.authorization.fragmentes

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import com.jakewharton.rxbinding.view.RxView
import com.softtech.android_structure.R
import com.softtech.android_structure.base.fragment.BaseFragment
import com.softtech.android_structure.features.authorization.vm.LoginFormState
import com.softtech.android_structure.features.authorization.vm.LoginViewModel
import com.softtech.android_structure.features.home.activities.HomeActivity
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.jakewharton.rxbinding.widget.RxTextView




class LoginFragment : BaseFragment(){
    private val loginViewModel :LoginViewModel by viewModel()
    override fun layoutResource(): Int =R.layout.fragment_login
    override fun onViewInflated(parentView: View, childView: View) {
        super.onViewInflated(parentView, childView)
        initObservers()
        initEventHandler()
    }

    private fun initEventHandler() {
        btnLogin.setOnClickListener {
            login()
        }


        RxTextView.textChanges(inputPassword)
            .subscribe { charSequence ->
                inputPassword.error=null
            }

        RxTextView.textChanges(inputUsername)
            .subscribe { charSequence ->
                inputUsername.error=null
            }
    }

    private fun initObservers() {
        loginViewModel.apply {

        }

    }


    fun login(){
            val  intent= Intent(requireContext(), HomeActivity::class.java)
            requireActivity().overridePendingTransition(0,0)
            startActivity(intent)
            requireActivity().overridePendingTransition(0,0)
            requireActivity().finish()
    }


}
