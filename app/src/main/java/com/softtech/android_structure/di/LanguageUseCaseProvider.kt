package com.softtech.android_structure.di

import android.content.Context
import com.softtech.android_structure.data.repositories.LocaleRepository
import com.softtech.android_structure.data.sources.local.AppPreference
import com.softtech.android_structure.data.sources.local.AppPreferenceConstants.PREFERENCE_FILE_NAME
import com.softtech.android_structure.domain.usecases.LanguageUseCases

object LanguageUseCaseProvider {

    private var languageUseCases :LanguageUseCases?=null

    fun getLanguageUseCase(context: Context):LanguageUseCases{
        if (languageUseCases==null){
            languageUseCases= LanguageUseCases(getLocalRepository(context))
        }
        return languageUseCases as LanguageUseCases
    }

    fun getLocalRepository(context:Context)=LocaleRepository(AppPreference(context.getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)))
}