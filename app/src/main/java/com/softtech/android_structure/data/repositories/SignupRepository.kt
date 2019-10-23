package com.softtech.android_structure.data.repositories

import com.softtech.android_structure.data.sources.remote.apis.RegisterAPI
import com.softtech.android_structure.domain.entities.account.RegisterParams
import io.reactivex.Single

class SignupRepository(private val registerAPI: RegisterAPI) {
    
    fun callRegisterAPI(registerParams: RegisterParams): Single<String> {
        return registerAPI.register(registerParams).map {response->response.message}
    }
}