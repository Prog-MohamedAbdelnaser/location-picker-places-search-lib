package com.softtech.android_structure.data.sources.remote.interceptor

import com.softtech.android_structure.di.DIConstants
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.koin.standalone.KoinComponent
import org.koin.standalone.get

class HeadersInterceptor(private val mainRepository: Any) : Interceptor, KoinComponent {

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