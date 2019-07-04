package com.nasmanpower.nas.data.sources.remote.apis

import com.nasmanpower.nas.entities.APIResponse
import com.nasmanpower.nas.entities.LoginParams
import com.nasmanpower.nas.entities.User
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginAPI {

    @POST("AccountApi/ClientLogin")
    fun login(@Body param: LoginParams): Single<APIResponse<User>>
}