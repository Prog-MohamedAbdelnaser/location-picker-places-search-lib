package com.nasmanpower.nas.data.sources.remote.apis

import com.nasmanpower.nas.entities.APIResponse
import com.nasmanpower.nas.entities.Home
import com.nasmanpower.nas.entities.MainSectors
import io.reactivex.Single
import retrofit2.http.POST

interface HomeAPI {

    @POST("SharedApi/DashboardPhoto")
    fun getHomePhoto(): Single<APIResponse<Home>>

    @POST("SharedApi/MainSectors")
    fun getMainSectors(): Single<APIResponse<List<MainSectors>>>

}