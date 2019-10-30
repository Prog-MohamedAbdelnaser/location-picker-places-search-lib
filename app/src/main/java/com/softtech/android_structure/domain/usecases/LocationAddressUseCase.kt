package com.softtech.android_structure.domain.usecases

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.softtech.android_structure.data.repositories.LocationAddressRepository
import io.reactivex.Single

class LocationAddressUseCase(private val locationAddressRepository: LocationAddressRepository) :
        UseCase<LatLng, Single<String>> {

    override fun execute(param: LatLng?): Single<String> =
            locationAddressRepository.getAddress(param!!)

}