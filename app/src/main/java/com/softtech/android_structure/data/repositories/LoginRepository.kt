package com.softtech.android_structure.data.repositories

import com.google.gson.Gson
import com.softtech.android_structure.data.sources.remote.apis.LoginAPI
import com.softtech.android_structure.data.sources.remote.apis.RegisterAPI
import com.softtech.android_structure.entities.account.LoginParameters
import com.softtech.android_structure.entities.account.RegisterParams
import io.reactivex.Single
import io.reactivex.functions.Function

class LoginRepository(private val loginAPI: LoginAPI,private val userRepository: UserRepository) {
    
    fun callLoginAPI(params:LoginParameters): Single<String> {
        return loginAPI.login(params).doOnSuccess {response->
            userRepository.saveUserData(response.payload!!)
        }.map {response->response.message}
    }
}