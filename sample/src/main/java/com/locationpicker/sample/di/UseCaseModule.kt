package com.locationpicker.sample.di

import com.locationpicker.sample.domain.usecases.LanguageUseCases
import org.koin.dsl.module.module


val useCaseModule = module {

    single { LanguageUseCases(get()) }

}