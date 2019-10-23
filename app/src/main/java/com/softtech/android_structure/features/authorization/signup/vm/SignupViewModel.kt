package com.softtech.android_structure.features.authorization.signup.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.softtech.android_structure.domain.entities.account.RegisterParams
import com.softtech.android_structure.domain.usecases.account.RegisterUseCase
import com.softtech.android_structure.features.common.CommonState
import io.reactivex.Flowable

class SignupViewModel(private val registerUseCase: RegisterUseCase):ViewModel() {

    val signupStateMutableLiveData=MutableLiveData<CommonState<String>>()
     var signupStateLiveData:LiveData<CommonState<String>> =signupStateMutableLiveData

    fun attempSignup(registerParams: RegisterParams){
        registerUseCase.register(registerParams,signupStateMutableLiveData)
    }

    override fun onCleared() {
        super.onCleared()
        registerUseCase.clearDispose()
    }
}