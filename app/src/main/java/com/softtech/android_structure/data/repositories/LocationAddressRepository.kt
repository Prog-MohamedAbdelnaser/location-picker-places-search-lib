package com.softtech.android_structure.data.repositories

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import com.softartch_lib.exceptions.NoAddressFoundException
import io.reactivex.Single
import java.io.IOException
import java.util.*

class LocationAddressRepository(
        private val context: Context,
        private val serviceNotFoundErrorMessage: String,
        private val invalidLatLongErrorMessage: String,
        private val noAddressFoundErrorMessage: String) {

    private val geoCoder: Geocoder= Geocoder(context, Locale.getDefault())

    fun getAddress(param: LatLng): Single<String> {
        val addresses: List<Address>

        try {
            addresses = geoCoder.getFromLocation(
                    param.latitude,
                    param.longitude,
                    1)
        } catch (ioException: IOException) {
            // Catch network or other I/O problems.
            return Single.error(IOException(serviceNotFoundErrorMessage, ioException))
        } catch (illegalArgumentException: IllegalArgumentException) {
            // Catch invalid latitude or longitude values.
            return Single.error(IllegalArgumentException(invalidLatLongErrorMessage, illegalArgumentException))
        }

        // Handle case where no address was found.
        return if (addresses.isEmpty()) {
            Single.error(NoAddressFoundException(noAddressFoundErrorMessage))
        } else {
            val address = addresses[0]
            // Fetch the address lines using getAddressLine,
            // join them, and send them to the thread.
            val addressFragments = with(address) {
                (0..maxAddressLineIndex).map { getAddressLine(it) }
            }
            Single.just(addressFragments.joinToString())
        }

    }

}