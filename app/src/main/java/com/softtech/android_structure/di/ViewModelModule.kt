package com.softtech.android_structure.di

import com.softtech.android_structure.features.common.LanguageViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {

  /*  viewModel { TimerViewModel() }*/

    viewModel { LanguageViewModel(get()) }

}