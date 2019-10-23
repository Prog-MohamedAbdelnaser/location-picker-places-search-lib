package com.softtech.android_structure.features.authorization.signup.di

import com.softtech.android_structure.data.repositories.SignupRepository
import com.softtech.android_structure.data.sources.remote.apis.RegisterAPI
import com.softtech.android_structure.domain.usecases.account.RegisterUseCase
import com.softtech.android_structure.domain.validations.RegisterValidator
import com.softtech.android_structure.domain.validations.ValidationConstants
import com.softtech.android_structure.features.authorization.signup.vm.SignupViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit

val signupModule= module {
    factory { RegisterValidator(get(),get(ValidationConstants.PASSWORD_MIN_LENGTH_VALIDATOR),get(ValidationConstants.FULL_NAME_MIN_LENGTH_VALIDATOR),get()) }
    factory { get<Retrofit>().create(RegisterAPI::class.java) }
    factory { SignupRepository(get()) }
    factory { RegisterUseCase(get(),get()) }
    viewModel { SignupViewModel(get()) }
}