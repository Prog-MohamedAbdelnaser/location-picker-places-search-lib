package com.softtech.android_structure.di

import android.content.Context
import com.nasmanpower.nas.data.sources.resources.AppResources
import com.softtech.android_structure.di.DIConstants.KEY_CURRENT_LANGUAGE
import com.softtech.android_structure.di.DIConstants.KEY_GLIDE_OKHTTP_CLIENT
import com.softtech.android_structure.data.repositories.LocaleRepository
import com.softtech.android_structure.data.repositories.StringsRepository
import com.softtech.android_structure.data.repositories.UserRepository
import com.softtech.android_structure.data.sources.local.AppPreference
import com.softtech.android_structure.data.sources.local.AppPreferenceConstants.DEFAULT_LOCALE
import com.softtech.android_structure.data.sources.local.AppPreferenceConstants.PREFERENCE_FILE_NAME
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

val applicationModule = module {

    single { AppResources(get()) }

    single { StringsRepository(get()) }

    single(PREFERENCE_FILE_NAME) { androidApplication().getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE) }

    single { AppPreference(get(PREFERENCE_FILE_NAME)) }

    factory { UserRepository(get()) }



    single { LocaleRepository(get()) }

    single(DEFAULT_LOCALE) { get<LocaleRepository>().getLocale() }

    factory(KEY_CURRENT_LANGUAGE) { get<LocaleRepository>().getLanguage()!! }


    single(KEY_GLIDE_OKHTTP_CLIENT) {
        OkHttpClient.Builder()
                .sslSocketFactory(
                        get<SSLContext>().socketFactory,
                        get<Array<TrustManager>>()[0] as X509TrustManager)
                .hostnameVerifier(get())
                .build()
    }

}