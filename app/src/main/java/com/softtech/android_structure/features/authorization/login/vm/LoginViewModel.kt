package com.softtech.android_structure.features.authorization.login.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.Transformations
import com.softtech.android_structure.R
import com.softtech.android_structure.domain.usecases.account.UserUseCase
import com.softtech.android_structure.entities.account.LoginParameters
import com.softtech.android_structure.entities.account.User
import com.softtech.android_structure.features.common.CommonState

class LoginViewModel(private val userUseCase: UserUseCase) : ViewModel() {


    private var loginState=MutableLiveData<CommonState<User>>()
     var  loginStateLiveData:LiveData<CommonState<User>> = loginState

    fun login(loginParameters: LoginParameters){
       userUseCase.login(loginParameters,loginState)
    }



}
