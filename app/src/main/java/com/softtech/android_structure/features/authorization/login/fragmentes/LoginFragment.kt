package com.softtech.android_structure.features.authorization.login.fragmentes

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.softtech.android_structure.domain.errors.ValidationTypeValues
import com.softtech.android_structure.R
import com.softtech.android_structure.base.fragment.BaseFragment
import com.softtech.android_structure.features.authorization.login.vm.LoginViewModel
import com.softtech.android_structure.features.home.activities.HomeActivity
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.jakewharton.rxbinding.widget.RxTextView
import com.softtech.android_structure.base.dialogs.AlertDialogManager
import com.softtech.android_structure.base.extension.handleApiErrorWithSnackBar
import com.softtech.android_structure.di.DIConstants
import com.softtech.android_structure.domain.entities.account.LoginParameters
import com.softtech.android_structure.domain.errors.CompositeValidationException
import com.softtech.android_structure.domain.errors.ValidationException
import com.softtech.android_structure.features.authorization.verification.activities.VerificationActivity
import com.softtech.android_structure.features.common.CommonState
import org.koin.standalone.get
import timber.log.Timber


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
           loginViewModel.login(getLoginParams())
        }


        tvForgetPassword.setOnClickListener {
            findNavController().navigate(R.id.forgetPasswordFragment)
        }

        tvRegister.setOnClickListener {
            findNavController().navigate(R.id.signupFragment)

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

    private fun getLoginParams()= LoginParameters(phoneNumber = inputUsername.text.toString(),password = inputPassword.text.toString(),deviceId = get(DIConstants.KEY_DEVICE_ID))

    fun navigateToVerificationScreen(){
        val intent=Intent(requireContext(),VerificationActivity::class.java)
        startActivityForResult(intent,VerificationActivity.VERIFICATION_REQUEST_CODE)
    }

    private fun initObservers() {
        loginViewModel.apply {
            loginState?.observe(this@LoginFragment, Observer {
                handleLoginState(it)
            })
        }

    }

    private fun handleLoginState(state: CommonState<String>?) {
        Log.i("handleLoginState","state is ${state.toString()}")
        when(state){
           is CommonState.LoadingShow->showProgressDialog()
           is CommonState.LoadingFinished->{hideProgressDialog() }
            is CommonState.Success->{
                navigateToVerificationScreen()
            }
            is CommonState.Error->{
                if (state.exception is ValidationException)
                    handleValidationException(state.exception)
                else
                    handleApiErrorWithSnackBar(state.exception)            }
        }
    }

    private fun handleValidationException(validationException: ValidationException) {
        println("handleValidationException ${validationException.message}")
        if (validationException is CompositeValidationException) {
            for (exception in validationException.validationExceptions) {
                handleValidationException(exception)
            }
        } else {
            when (validationException.validationType) {
                ValidationTypeValues.PHONE -> inputUsername.error = validationException.message
                ValidationTypeValues.PASSWORD -> inputPassword.error = validationException.message
                else -> AlertDialogManager.showAlertMessage(requireContext(),validationException.message)
            }
        }
    }


    fun login(){
            val  intent= Intent(requireContext(), HomeActivity::class.java)
            startActivity(intent)
            requireActivity().overridePendingTransition(0,0)
            requireActivity().finish()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== Activity.RESULT_OK && requestCode ==VerificationActivity.VERIFICATION_REQUEST_CODE) {
            Timber.i("onActivityResult")
            login()
        }
    }
}
