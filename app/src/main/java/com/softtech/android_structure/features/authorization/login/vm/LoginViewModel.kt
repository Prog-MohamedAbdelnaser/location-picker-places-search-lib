package com.softtech.android_structure.features.authorization.login.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softtech.android_structure.domain.entities.account.LoginParameters
import com.softtech.android_structure.domain.entities.account.User
import com.softtech.android_structure.domain.usecases.account.LoginUseCase
import com.softtech.android_structure.features.common.CommonState
import kotlinx.coroutines.launch

class LoginViewModel(private val userUseCase: LoginUseCase) : ViewModel() {


     var loginState= MutableLiveData<CommonState<String>>()
     var  loginStateLiveData:LiveData<CommonState<String>> ?= loginState

    fun login(loginParameters: LoginParameters){

        viewModelScope.launch {
            userUseCase.login(loginParameters,loginState)
        }
    }

}
