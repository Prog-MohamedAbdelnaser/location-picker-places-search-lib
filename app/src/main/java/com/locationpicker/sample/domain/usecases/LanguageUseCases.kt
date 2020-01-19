package com.locationpicker.sample.domain.usecases

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import com.locationpicker.sample.data.repositories.LocaleRepository
import timber.log.Timber
import java.util.*

class LanguageUseCases(private val localeRepository: LocaleRepository) {

    fun changeLanguageTo(langaugeName: String){
        Timber.i("language name ${langaugeName.toString().toLowerCase()}")
        localeRepository.setLanguage(langaugeName)
    }
     fun execute(param: Context?): Context {

        var newContext = param

        val language = localeRepository.getLanguage()

        if (!language.isNullOrEmpty()) {

            val locale = Locale(language.toLowerCase())

            val res = param!!.resources

            var configuration = res.configuration

            Locale.setDefault(locale)

            configuration.setLocale(locale)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                val localeList = LocaleList(locale)

                LocaleList.setDefault(localeList)

                configuration.setLocales(localeList)
            }

            newContext = param.createConfigurationContext(configuration)
        }

        return newContext!!
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

    fun getLanguage()=localeRepository.getLanguage()

   private fun setSystemLocale(config: Configuration, locale: Locale) {
        Locale.setDefault(locale)
        config.setLocale(locale)
    }
}
