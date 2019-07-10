package com.softtech.android_structure.data.repositories

import com.softtech.android_structure.data.repositories.RepositoriesConstants.KEY_LANGUAGE_CODE
import com.softtech.android_structure.data.repositories.RepositoriesConstants.LANGUAGE_ENGLISH
import com.softtech.android_structure.data.sources.local.AppPreference
import com.softtech.android_structure.entities.AppLanguages
import java.util.*


class LocaleRepository(private val appPreference: AppPreference) {

    fun setLanguage(newLanguage: String) = appPreference.putStringWithCommit(KEY_LANGUAGE_CODE, newLanguage.toString())

    fun getLanguage(): String = appPreference.getString(KEY_LANGUAGE_CODE, LANGUAGE_ENGLISH)!!

    fun isRtl(): Boolean = getLanguage() == AppLanguages.AR.toString()

    fun getLocale(): Locale = Locale(getLanguage())

}

