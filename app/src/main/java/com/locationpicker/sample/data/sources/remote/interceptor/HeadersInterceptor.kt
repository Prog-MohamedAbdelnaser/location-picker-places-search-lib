package com.softtech.sample.data.sources.remote.interceptor

import com.locationpicker.sample.di.DIConstants
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.koin.standalone.KoinComponent
import org.koin.standalone.get

class HeadersInterceptor() : Interceptor, KoinComponent {

    private val keyAuthorization = "authorization"

    private val keyApiKey = "apiKey"
    private val apiKeyValue = "Nas@manpoweragent"
    private val keyLanguage = "Language"

    override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(createNewRequestWithApiKey(chain.request()))

    private fun createNewRequestWithApiKey(oldRequest: Request): Request {
        val requestBuilder = oldRequest.newBuilder()
                .addHeader(keyApiKey, apiKeyValue)
                .addHeader(keyLanguage, get(DIConstants.KEY_CURRENT_LANGUAGE))
      /*  mainRepository.getCurrentLoggedInUser()?.apply {
          //  requestBuilder.addHeader(keyAuthorization, "Bearer ${this.accessToken}")
        }*/
        return requestBuilder.build()
    }
}