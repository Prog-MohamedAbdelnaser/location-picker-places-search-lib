package com.locationpicker.sample.data.repositories

import com.locationpicker.sample.data.repositories.RepositoriesConstants.KEY_LANGUAGE_CODE
import com.locationpicker.sample.data.repositories.RepositoriesConstants.LANGUAGE_ARABIC
import com.locationpicker.sample.data.repositories.RepositoriesConstants.LANGUAGE_ENGLISH
import com.locationpicker.sample.data.sources.local.AppPreference
import com.locationpicker.sample.domain.entities.AppLanguages
import java.util.*


class LocaleRepository(private val appPreference: AppPreference) {

    fun setLanguage(newLanguage: String) = appPreference.putStringWithCommit(KEY_LANGUAGE_CODE, newLanguage.toString())

    fun getLanguage(): String = appPreference.getString(KEY_LANGUAGE_CODE, LANGUAGE_ENGLISH)!!

    fun isRtl(): Boolean = getLanguage() == AppLanguages.AR.toString()

    fun getLocale(): Locale = Locale(getLanguage())

}

