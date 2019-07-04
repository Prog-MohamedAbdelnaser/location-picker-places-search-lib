package com.nasmanpower.nas.data.sources.remote.apis

import com.nasmanpower.nas.entities.APIResponse
import com.nasmanpower.nas.entities.IdNumberType
import com.nasmanpower.nas.entities.RegisterParams
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterAPI {

    @POST("SharedApi/IdNumberType")
    fun getIdNumberTypes(): Single<APIResponse<List<IdNumberType>>>

    @POST("AccountApi/ClientRegister")
    fun register(@Body param: RegisterParams): Single<APIResponse<String>>

}