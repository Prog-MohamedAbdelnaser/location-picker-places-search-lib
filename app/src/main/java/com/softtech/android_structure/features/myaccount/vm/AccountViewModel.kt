package com.softtech.android_structure.features.myaccount.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.softtech.android_structure.domain.entities.account.User
import com.softtech.android_structure.domain.usecases.account.UserUseCase
import com.softtech.android_structure.features.common.CommonState

class AccountViewModel(private val userUseCase: UserUseCase) : ViewModel() {


    private var userState=MutableLiveData<CommonState<User>>()
    val userStateLiveData : LiveData<CommonState<User>> = userUseCase.getLogedUser()

    fun clearUserData(){
        userUseCase.clearUser()
    }


}