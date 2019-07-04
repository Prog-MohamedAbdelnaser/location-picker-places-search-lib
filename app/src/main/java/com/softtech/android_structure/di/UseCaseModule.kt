package com.softtech.android_structure.di

import com.softtech.android_structure.domain.usecases.LanguageUseCases
import org.koin.dsl.module.module


val useCaseModule = module {

    single { LanguageUseCases(get()) }

}