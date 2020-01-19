package com.locationpicker.sample.di

import android.content.Context
import com.locationpicker.sample.data.repositories.LocaleRepository
import com.locationpicker.sample.data.sources.local.AppPreference
import com.locationpicker.sample.data.sources.local.AppPreferenceConstants.PREFERENCE_FILE_NAME
import com.locationpicker.sample.domain.usecases.LanguageUseCases

object LanguageUseCaseProvider {

    private var languageUseCases : LanguageUseCases?=null

    fun getLanguageUseCase(context: Context):LanguageUseCases{
        if (languageUseCases==null){
            languageUseCases= LanguageUseCases(getLocalRepository(context))
        }
        return languageUseCases as LanguageUseCases
    }

    fun getLocalRepository(context:Context)=
        LocaleRepository(AppPreference(context.getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)))
}