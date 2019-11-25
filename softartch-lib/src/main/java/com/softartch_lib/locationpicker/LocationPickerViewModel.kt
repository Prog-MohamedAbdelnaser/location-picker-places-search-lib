package com.softartch_lib.locationpicker

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.softartch_lib.component.RequestDataState
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class LocationPickerViewModel(private val locationAddressUseCase: LocationAddressUseCase) : ViewModel() {

    private val disposables=CompositeDisposable()
    var lastSelectedLocation :LatLng?=null
    var targetAddress:String?=null

    var targetLocationAddress: LocationAddress?=null

    var locationAddressLiveDataState =MutableLiveData<RequestDataState<LocationAddress>>()



    companion object {
        const val ONE_METER = 1
    }

    private val locationAddressSubject = PublishSubject.create<LatLng>()



    init {


        disposables.add(locationAddressSubject
            .filter {
                val filter =
                    if (lastSelectedLocation == null) {
                        true
                    } else {
                        SphericalUtil.computeDistanceBetween(
                            LatLng(lastSelectedLocation!!.latitude,
                                lastSelectedLocation!!.longitude),
                            LatLng(it.latitude, it.longitude)) > ONE_METER
                    }
                if (filter) {
                    locationAddressLiveDataState.postValue(RequestDataState.LoadingShow)
                }
                filter
            }
            .debounce(300, TimeUnit.MILLISECONDS)
            .subscribe {
                fetchLocationAddress(it)
            })
    }



    fun onCameraIdle(location: LatLng) {
        if (targetAddress == null) {
            locationAddressSubject.onNext(location)
        } else {
            locationAddressLiveDataState.postValue(RequestDataState.Success(targetLocationAddress!!))
        }
    }

    fun fetchLocationAddress(latlng: LatLng?) {

        lastSelectedLocation = latlng
        disposables.add(locationAddressUseCase.execute(latlng)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
    Log.i("Location","fetchLocationAddress$it")
    targetAddress = it
                targetLocationAddress =LocationAddress(latlng!!.latitude,latlng.longitude,it)
    locationAddressLiveDataState.value =RequestDataState.Success(targetLocationAddress!!)

}, {
    it.printStackTrace()
    locationAddressLiveDataState.value = RequestDataState.Error(it)
})
)
}


}