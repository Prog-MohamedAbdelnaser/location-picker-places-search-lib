package com.softtech.android_structure.data.repositories

import com.softtech.android_structure.data.sources.remote.apis.LoginAPI
import com.softtech.android_structure.domain.entities.account.LoginParameters
import com.softtech.android_structure.domain.entities.account.User
import io.reactivex.Single

class LoginRepository(private val loginAPI: LoginAPI,private val userRepository: UserRepository) {
    
    fun runAPI(params:LoginParameters): Single<String> {
        return Single.fromCallable{"Success Login"}.doOnSuccess { userRepository.saveUserData(User("1",params.phoneNumber)) }
   /*     return loginAPI.login(params).doOnSuccess {response->
            userRepository.saveUserData(response.payload!!)
        }.map {response->response.message}*/
    }
}