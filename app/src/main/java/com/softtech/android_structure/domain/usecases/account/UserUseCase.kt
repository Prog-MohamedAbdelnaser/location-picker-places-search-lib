package com.softtech.android_structure.domain.usecases.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.softtech.android_structure.application.MainModel
import com.softtech.android_structure.data.repositories.UserRepository
import com.softtech.android_structure.entities.account.LoginParameters
import com.softtech.android_structure.entities.account.User
import com.softtech.android_structure.features.common.CommonState
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserUseCase(private val userRepository: UserRepository) : MainModel() {

    fun login(loginParameters: LoginParameters):LiveData<CommonState<User>>{
        val loginLiveData=MutableLiveData<CommonState<User>>()
        loginLiveData.value=CommonState.LoadingShow
       userRepository.saveUserData(User("1",loginParameters.userName))
        loginLiveData.value=CommonState.LoadingFinished
        loginLiveData.value=CommonState.Success(User("1",loginParameters.userName))
        return loginLiveData
    }


    fun getLogedUser(userState: MutableLiveData<CommonState<User>>):LiveData<CommonState<User>>{
        val userLiveData=MutableLiveData<CommonState<User>>()
        getDisposable(userLiveData,false)!!.
            add(getUserSingle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { userLiveData.value=CommonState.LoadingShow }
                .doFinally { userLiveData.value=CommonState.LoadingFinished }
                .subscribe({userLiveData.value=CommonState.Success(it)},{userLiveData.value=CommonState.Error(it)})
        )
        return userLiveData
    }
    fun clearUser(){
        userRepository.clear()
    }
    private fun  getUserSingle(): Single<User> =Single.fromCallable{  userRepository.getLogedInUser() }
}