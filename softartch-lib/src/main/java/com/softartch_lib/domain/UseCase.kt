package com.softartch_lib.domain

import io.reactivex.disposables.CompositeDisposable

interface UseCase<in P, R> {

    fun getDisposabel()=CompositeDisposable()
    fun execute(param: P? = null): R
    fun clearDispose(){
        getDisposabel().dispose()
    }
}