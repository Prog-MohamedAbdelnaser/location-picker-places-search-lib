package com.softtech.android_structure.features.authorization.login.di

import com.softtech.android_structure.data.repositories.LoginRepository
import com.softtech.android_structure.data.sources.remote.apis.LoginAPI
import com.softtech.android_structure.domain.usecases.account.LoginUseCase
import com.softtech.android_structure.domain.validations.LoginValidator
import com.softtech.android_structure.domain.validations.ValidationConstants
import com.softtech.android_structure.features.authorization.login.vm.LoginViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit

// Created by mohamed abdelnaser on 7/4/19.
val loginModule= module {
    factory { LoginValidator(get(),get(ValidationConstants.PASSWORD_MIN_LENGTH_VALIDATOR),get(ValidationConstants.MIN_LENGTH_VALIDATOR)) }
    factory { get<Retrofit>().create(LoginAPI::class.java) }
    factory { LoginRepository(get(),get()) }
    factory { LoginUseCase(get(),get()) }
    viewModel { LoginViewModel(get()) }
}