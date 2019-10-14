package com.softtech.android_structure.application

import android.app.Activity
import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import com.nasmanpower.nas.data.sources.remote.di.remoteModule
import com.softtech.android_structure.BuildConfig
import com.softtech.android_structure.di.*
import com.softtech.android_structure.features.authorization.login.di.loginModule
import com.softtech.android_structure.features.myaccount.di.myAccountModule
import com.softtech.android_structure.features.splash.di.splashModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class ApplicationClass : Application() {
    companion object {
        var currentActivity: ComponentName? = null
        var isTestVersion:Boolean=true
        lateinit var appContext:Application
        lateinit var application:ApplicationClass
    }

    override fun onCreate() {
        super.onCreate()
        appContext=this
        startKoin(
                this,
                listOf(
                        applicationModule,
                        remoteModule,
                        validatorModule,
                        viewModelModule,
                        useCaseModule,
                        splashModule,
                        loginModule,
                    myAccountModule
                )
        )

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }


        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {

            override fun onActivityPaused(activity: Activity?) {
            }

            override fun onActivityResumed(activity: Activity?) {
                currentActivity = activity?.componentName
            }

            override fun onActivityStarted(activity: Activity?) {
            }

            override fun onActivityDestroyed(activity: Activity?) {
                currentActivity = null
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            }

            override fun onActivityStopped(activity: Activity?) {
            }

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            }

        })
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LanguageUseCaseProvider.getLanguageUseCase(base).wrap(base))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LanguageUseCaseProvider.getLanguageUseCase(this).wrap(this)
    }

    var versionCode = BuildConfig.VERSION_CODE
    var versionName = BuildConfig.VERSION_NAME
}