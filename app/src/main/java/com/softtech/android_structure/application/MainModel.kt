package com.softtech.android_structure.application

import androidx.lifecycle.MutableLiveData
import com.softtech.android_structure.base.extension.isInternetConnected
import com.softtech.android_structure.features.common.CommonState
import io.reactivex.disposables.CompositeDisposable

abstract class MainModel {


    private val disposable: CompositeDisposable = CompositeDisposable()
    fun getDisposable(any: Any, showProgress: Boolean): CompositeDisposable? {
        return if (isInternetConnected()) {
            if (showProgress)
                (any as MutableLiveData<*>).value = CommonState.LoadingShow
            disposable
        } else {
            (any as MutableLiveData<*>).value = CommonState.NoInternet
            null
        }
    }

    fun clear() {
        disposable.clear()
    }



}