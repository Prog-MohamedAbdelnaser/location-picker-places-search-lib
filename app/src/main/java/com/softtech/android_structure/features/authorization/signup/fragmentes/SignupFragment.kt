package com.softtech.android_structure.features.authorization.signup.fragmentes

import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.softtech.android_structure.domain.errors.ValidationTypeValues

import com.softtech.android_structure.R
import com.softtech.android_structure.base.dialogs.AlertDialogManager
import com.softtech.android_structure.base.extension.handleApiErrorWithSnackBar
import com.softartch_lib.component.fragment.BaseFragment
import com.softtech.android_structure.di.DIConstants
import com.softtech.android_structure.domain.entities.account.RegisterParams
import com.softtech.android_structure.domain.errors.CompositeValidationException
import com.softtech.android_structure.domain.errors.ValidationException
import com.softtech.android_structure.features.authorization.signup.vm.SignupViewModel
import com.softtech.android_structure.features.authorization.verification.activities.VerificationActivity
import com.softtech.android_structure.features.common.CommonState
import com.softtech.android_structure.features.temp.activities.LocationActivity
import kotlinx.android.synthetic.main.fragment_signup.*
import kotlinx.android.synthetic.main.fragment_signup.inputPassword
import kotlinx.android.synthetic.main.fragment_signup.inputUsername
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.standalone.get


class SignupFragment : BaseFragment() {
    private val signupViewModel:SignupViewModel by viewModel()
    override fun layoutResource(): Int =R.layout.fragment_signup
    override fun onViewInflated(parentView: View, childView: View) {
        super.onViewInflated(parentView, childView)

        initObservers()
        initEventHandler()

    }

    private fun initEventHandler() {
        tvBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        tvLogin.setOnClickListener {
            requireActivity().onBackPressed()
        }
        btnSignUp.setOnClickListener {
            signupViewModel.attempSignup(createRegisterParams())
        }

        tvAddress.setOnClickListener {
            LocationActivity.startLocationPicker(requireActivity())
        }

    }

    private fun createRegisterParams(): RegisterParams {
        return RegisterParams(inputUsername.text.toString(),inputPassword.text.toString(),
            inputPhoneNumber.text.toString(),0.0,0.0,"Giza",inputEmail.text.toString()
        ,false,get(DIConstants.KEY_DEVICE_ID))
    }

    private fun initObservers() {
        signupViewModel.apply {
            signupStateMutableLiveData.observe(this@SignupFragment, Observer {
                handleResponseState(it)
            })
        }
    }

    private fun handleResponseState(state: CommonState<String>?) {
        Log.i("handleLoginState","state is ${state.toString()}")
        when(state){
            is CommonState.LoadingShow->showProgressDialog()
            is CommonState.LoadingFinished->{hideProgressDialog() }
            is CommonState.Success->{
               VerificationActivity. navigateToVerificationScreen(requireActivity())
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
                ValidationTypeValues.PHONE -> inputPhoneNumber.error = validationException.message
                ValidationTypeValues.PASSWORD -> inputPassword.error = validationException.message
                ValidationTypeValues.EMAIL -> inputEmail.error = validationException.message
                ValidationTypeValues.USER_NAME -> inputUsername.error = validationException.message

                else -> AlertDialogManager.showAlertMessage(requireContext(),validationException.message)
            }
        }
    }
}
