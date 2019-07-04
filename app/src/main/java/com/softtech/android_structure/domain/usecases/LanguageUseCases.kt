package com.softtech.android_structure.domain.usecases

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import com.softtech.android_structure.data.repositories.LocaleRepository
import com.softtech.android_structure.entities.AppLanguages
import timber.log.Timber
import java.util.*

class LanguageUseCases(private val localeRepository: LocaleRepository) {

    fun changeLanguageTo(langaugeName: AppLanguages){
        Timber.i("language name ${langaugeName.toString().toLowerCase()}")
        localeRepository.setLanguage(langaugeName)
    }

    fun wrap(context: Context): Context {

        val config = context.resources.configuration

        val language = localeRepository.getLanguage().toLowerCase()

        val resourc = context.resources

        val dm = resourc.displayMetrics

        if (language != "") {
            val locale = Locale(language)
            Locale.setDefault(locale)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                setSystemLocale(config, locale)
            } else {
                Locale.setDefault(locale)
                config.setLocale(locale)
            }
            resourc.updateConfiguration(config, dm)

        }
        return context.createConfigurationContext(config)
    }

   private fun setSystemLocale(config: Configuration, locale: Locale) {
        Locale.setDefault(locale)
        config.setLocale(locale)
    }
}
