package com.locationpicker.sample.features.splash.di

import com.locationpicker.sample.features.splash.vm.SplashViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


// Created by mohamed abdelnaser on 7/4/19.

val splashModule= module {
    viewModel {  SplashViewModel() }
}
