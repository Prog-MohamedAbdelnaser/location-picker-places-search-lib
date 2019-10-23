package com.softtech.android_structure.features.myaccount.di

import com.softtech.android_structure.domain.usecases.account.UserUseCase
import com.softtech.android_structure.features.myaccount.vm.AccountViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val myAccountModule= module {
    factory { UserUseCase(get()) }
    viewModel { AccountViewModel(get()) }
}