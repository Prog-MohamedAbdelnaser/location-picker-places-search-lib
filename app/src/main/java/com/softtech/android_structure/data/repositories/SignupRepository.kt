package com.softtech.android_structure.data.repositories

import com.google.gson.Gson
import com.softtech.android_structure.data.sources.remote.apis.RegisterAPI
import com.softtech.android_structure.entities.account.RegisterParams
import io.reactivex.Single
import io.reactivex.functions.Function

class SignupRepository(private val registerAPI: RegisterAPI) {
    
    fun register(registerParams: RegisterParams): Single<String> {
        return registerAPI.register(registerParams).map {response->response.message}
    }
}