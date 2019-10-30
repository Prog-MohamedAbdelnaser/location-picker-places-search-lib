package com.softtech.android_structure.features.temp.vm

import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.softtech.android_structure.domain.usecases.LocationAddressUseCase
import com.softtech.android_structure.features.common.CommonState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.sql.Time

class LocationViewModel(private val locationAddressUseCase: LocationAddressUseCase) : ViewModel() {

    private val disposables=CompositeDisposable()
    var lastSelectedLocation :LatLng?=null
    var targetAddress:String?=null
    var locationAddressLiveDataState =MutableLiveData<CommonState<String>>()
     fun fetchLocationAddress(it: LatLng?) {
        lastSelectedLocation = it
        disposables.add(locationAddressUseCase.execute(it)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                Log.i("Location","LoadingShow")

                locationAddressLiveDataState.value=CommonState.LoadingShow }
            .doFinally {
                Log.i("Location","LoadingFinished")
                locationAddressLiveDataState.value=CommonState.LoadingFinished }
            .subscribe({
                Log.i("Location","fetchLocationAddress$it")
                targetAddress = it
                locationAddressLiveDataState.value = CommonState.Success(it)
            }, {
                it.printStackTrace()
                locationAddressLiveDataState.value = CommonState.Error(it)
            })
        )
    }
}