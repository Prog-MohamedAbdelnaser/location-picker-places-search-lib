package com.softtech.android_structure.data.sources.remote.apis

import com.softtech.android_structure.domain.entities.APIResponse
import com.softtech.android_structure.domain.entities.account.LoginParameters
import com.softtech.android_structure.domain.entities.account.User
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface VerificationAPI {
    @POST("login")
    fun login(@Body param: LoginParameters): Single<APIResponse<User>>

}