package com.softtech.android_structure.base.activity

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.softtech.android_structure.di.LanguageUseCaseProvider

abstract class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LanguageUseCaseProvider.getLanguageUseCase(base).execute(base))
    }


    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
        if (overrideConfiguration != null) {
            val uiMode = overrideConfiguration.uiMode
            overrideConfiguration.setTo(baseContext.resources.configuration)
            overrideConfiguration.uiMode = uiMode
        }
        super.applyOverrideConfiguration(overrideConfiguration)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resetTitles()
    }

    private fun resetTitles() {
        try {
            val info = packageManager.getActivityInfo(componentName, PackageManager.GET_META_DATA)
            if (info.labelRes != 0) {
                setTitle(info.labelRes)
            }
        } catch (ignored: PackageManager.NameNotFoundException) {
        }

    }

}