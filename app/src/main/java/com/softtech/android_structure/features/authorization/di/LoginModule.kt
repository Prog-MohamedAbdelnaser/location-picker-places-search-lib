package com.softtech.android_structure.features.authorization.di

import com.softtech.android_structure.domain.usecases.account.UserUseCase
import com.softtech.android_structure.features.authorization.vm.LoginViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

// Created by mohamed abdelnaser on 7/4/19.
val loginModule= module {
    factory { UserUseCase(get()) }
    viewModel { LoginViewModel(get()) }
}