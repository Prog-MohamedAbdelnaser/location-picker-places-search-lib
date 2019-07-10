package com.softtech.android_structure.features.common

import androidx.lifecycle.ViewModel
import com.softtech.android_structure.domain.usecases.LanguageUseCases

class LanguageViewModel(private val languageUseCases: LanguageUseCases) : ViewModel() {

    fun setLanguage(languageCode: String) = languageUseCases.changeLanguageTo(languageCode)

    fun getLanguage() = languageUseCases.getLanguage()

}