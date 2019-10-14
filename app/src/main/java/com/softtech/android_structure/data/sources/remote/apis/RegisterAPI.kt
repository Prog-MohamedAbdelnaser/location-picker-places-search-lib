package com.softtech.android_structure.data.sources.remote.apis

import com.softtech.android_structure.entities.APIResponse
import com.softtech.android_structure.entities.account.RegisterParams
import com.softtech.android_structure.entities.account.User
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterAPI {

    @POST("register")
    fun register(@Body param: RegisterParams): Single<APIResponse<User>>

}