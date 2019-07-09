package com.softtech.android_structure.features.splash.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.softtech.android_structure.data.repositories.UserRepository

// Created by mohamed abdelnaser on 7/4/19.
class SplashViewModel(private val userRepository: UserRepository) :ViewModel(){

    private val isLogedIn=MutableLiveData<Boolean>()
    val isLogedInLiveData : LiveData<Boolean> = isLogedIn

    init {
        getLogedInUser()
    }
    fun getLogedInUser(){
        isLogedIn.value=userRepository.isLoged()
    }

}