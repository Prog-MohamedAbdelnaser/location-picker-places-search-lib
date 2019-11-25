package com.softartch_lib.locationpicker

import com.google.android.gms.maps.model.LatLng
import com.softartch_lib.domain.UseCase
import io.reactivex.Single

class LocationAddressUseCase(private val locationAddressRepository: LocationAddressRepository) :
    UseCase<LatLng, Single<String>> {

    override fun execute(param: LatLng?): Single<String> =
            locationAddressRepository.getAddress(param!!)

}