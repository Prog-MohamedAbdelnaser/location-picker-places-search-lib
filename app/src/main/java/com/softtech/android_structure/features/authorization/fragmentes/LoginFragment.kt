package com.softtech.android_structure.features.authorization.fragmentes

import android.content.Intent
import android.os.Handler
import android.os.Looper
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
import com.softtech.android_structure.entities.account.LoginParameters
import com.softtech.android_structure.entities.account.User
import com.softtech.android_structure.features.common.CommonState
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
            if (validate()) loginViewModel.setLogin(getLoginParams())
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

    private fun getLoginParams()=LoginParameters(inputUsername.text.toString(),inputPassword.text.toString())


    private fun initObservers() {
        loginViewModel.apply {
            user.observe(this@LoginFragment, Observer {
                handleLoginState(it)
            })
        }

    }

    private fun handleLoginState(state: CommonState<User>?) {
        Timber.i("state is ${state.toString()}")
        when(state){
           is CommonState.LoadingShow->showProgressDialog()
           is CommonState.LoadingFinished->{}
            is CommonState.Success->{ Handler(Looper.getMainLooper()).postDelayed({
                hideProgressDialog()
                login()},4000) }
        }
    }

    fun validate(): Boolean {
        var  isValid=true
        if (inputUsername.length()<3){
            inputUsername.error=getString(R.string.invalid_username)
            isValid=false
        }
        if (inputPassword.length()<3){
            inputPassword.error=getString(R.string.invalid_password)
            isValid=false
        }

        return isValid
    }

    fun login(){
            val  intent= Intent(requireContext(), HomeActivity::class.java)
            requireActivity().overridePendingTransition(0,0)
            startActivity(intent)
            requireActivity().overridePendingTransition(0,0)
            requireActivity().finish()
    }


}
