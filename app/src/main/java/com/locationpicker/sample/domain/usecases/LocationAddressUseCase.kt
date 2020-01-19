package com.locationpicker.sample.domain.usecases

import com.google.android.gms.maps.model.LatLng
import com.locationpicker.sample.data.repositories.LocationAddressRepository
import io.reactivex.Single

class LocationAddressUseCase(private val locationAddressRepository: LocationAddressRepository) :
        UseCase<LatLng, Single<String>> {

    override fun execute(param: LatLng?): Single<String> =
            locationAddressRepository.getAddress(param!!)

}