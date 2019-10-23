package com.softtech.android_structure.domain.usecases

import com.softtech.android_structure.features.common.CommonState
import io.reactivex.disposables.CompositeDisposable

interface UseCase<in P, R> {

    fun getDisposabel()=CompositeDisposable()
    fun execute(param: P? = null): R
    fun clearDispose(){
        getDisposabel().dispose()
    }
}