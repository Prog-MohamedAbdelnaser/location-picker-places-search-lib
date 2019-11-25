package com.softtech.android_structure.features.temp.fragmentes

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.LocationSource
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.softtech.android_structure.R
import com.softartch_lib.component.fragment.BaseFragment
import com.softtech.android_structure.base.utility.GpsUtility
import com.softtech.android_structure.base.utility.MapUtility
import com.softtech.android_structure.features.common.CommonState
import com.softtech.android_structure.features.common.hideKeyboard
import com.softtech.android_structure.features.common.showErrorSnackbar
import com.softtech.android_structure.features.temp.vm.LocationViewModel
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Single
import io.reactivex.SingleSource
import kotlinx.android.synthetic.main.fragment_location.*
import timber.log.Timber
import io.reactivex.functions.Function
import org.koin.androidx.viewmodel.ext.android.viewModel


class LocationFragment : BaseFragment(), OnMapReadyCallback {

    companion object {

        const val providerChanges = "android.location.PROVIDERS_CHANGED"
        const val defaultZoom=15f

    }

    private var zoom:Float?= null

    private val locationViewModel : LocationViewModel by viewModel()

    private var userLocation: Location?=null

    private var gpsReceiver: GpsLocationReceiver? = null

    private var savedInstanceState: Bundle? = null

    private var locationRequest :LocationRequest?= null

    private var targetMarker:Marker?=null

    private var map:GoogleMap?=null

    private var locationUpdateStarted: Boolean = false

    private var fusedLocationClient: FusedLocationProviderClient? = null

    private var locationCallback: LocationCallback? = null

    private var locationChangedListener: LocationSource.OnLocationChangedListener? = null

    private var shouldMarkerFollowUserLocation: Boolean = false

    private var tvAddress:TextView?=null

    override fun layoutResource(): Int =R.layout.fragment_location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.savedInstanceState=savedInstanceState
    }
    override fun onViewInflated(parentView: View, childView: View) {
        super.onViewInflated(parentView, childView)
        /*val  mapFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
         mapFragment?.getMapAsync(this)*/
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        initObservers()

    }

    private fun initObservers() {
        locationViewModel.apply {
            locationAddressLiveDataState.observe(
                this@LocationFragment, Observer {
                handleLocationAddressState(it)
            })
        }
    }

    private fun handleLocationAddressState(state: CommonState<String>?) {
        when(state){
            is CommonState.LoadingShow->{showProgressDialog()}
            is CommonState.LoadingFinished->{hideProgressDialog()}
            is CommonState.Success->{
                Log.i("Location","handleLocationAddressState ${state.data}")
                tvAddress?.text=state.data
            }
            is CommonState.Error->{
                state.exception.message?.let { showErrorSnackbar(view!!, it) }
            }
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        println("onMapReady")
        this.map=map
        map!!.mapType = GoogleMap.MAP_TYPE_TERRAIN
        setupGoogleMap(map!!)
        checkPermission()

    }
    private fun checkPermission(){


        val rxPermissions = RxPermissions(this)
            rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
                .doOnNext {
                        granted ->
                    if (!granted) {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
                            checkPermission()
                        }else{
                            // PopupPermission(requireContext()).show()
                        }
                    }else {
                        Timber.i("permission granted$granted")
                        listenToGPSChanges()
                    }
                }.map(requestLocationServiceSettingFunction()).
                    subscribe({
                        startLocationTracking(map)
                        Timber.i("requestLocationServiceSettingFunction")

                    },{
                        it.printStackTrace()
                    })
    }

    private fun requestLocationServiceSettingFunction() =
        Function<Boolean, SingleSource<Boolean>> { granted ->
            if (granted) {
                GpsUtility.requsetEnableLocationServiceSetting(requireContext())
            } else {
                Single.error(IllegalArgumentException("Permissions must be granted first"))
            }
        }
    private fun listenToGPSChanges() {
        requireActivity().apply {
            gpsReceiver = GpsLocationReceiver()
           registerReceiver(gpsReceiver, IntentFilter(providerChanges))
       }
    }
    private fun setupGoogleMap(googleMap: GoogleMap) {
        googleMap?.apply {
            uiSettings.setAllGesturesEnabled(true)
            uiSettings.isMyLocationButtonEnabled=true
            uiSettings.isZoomControlsEnabled=true
            setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
                @SuppressLint("InflateParams")
                override fun getInfoContents(p0: Marker?): View? {
                    if (tvAddress == null) {
                        tvAddress = LayoutInflater
                            .from(context)
                            .inflate(R.layout.location_address, null, false)
                                as AppCompatTextView?
                    }
                    return tvAddress
                }

                override fun getInfoWindow(p0: Marker?): View? = null

            })

            setOnMapClickListener {
                Log.i("Location","setOnMapClickListener$it")

                shouldMarkerFollowUserLocation = false
              //  addTargetMarker(it)
            }


            setOnCameraIdleListener {

                Log.i("LocationFragmentLog","OnCameraIdleListener")
            }




        }
    }

    private fun startLocationTracking(googleMap: GoogleMap?) {
        if (fusedLocationClient == null) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)
        }
        if (locationCallback == null) {
            locationCallback = locationCallback()
        }
        enableGoogleMapMyLocation(googleMap, locationSource())
    }

    private fun enableGoogleMapMyLocation(googleMap: GoogleMap?, locationSource: LocationSource) {
        googleMap?.apply {
            setLocationSource(locationSource)
            isMyLocationEnabled = true

            setOnMyLocationButtonClickListener {
                userLocation?.apply {

                    val latLng = LatLng(latitude, longitude)
                    Log.i("Location","setOnMyLocationButtonClickListener ${latLng.toString()}")

                    if (targetMarker == null || shouldMarkerFollowUserLocation.not()) {
                        //targetAccuracy = accuracy
                        shouldMarkerFollowUserLocation = true
                      //  addTargetMarker(latLng)
                    } else {
                        MapUtility.moveCamera(googleMap, latLng)
                    }
                }
                true
            }

            setOnMyLocationClickListener {
                Log.i("Location","setOnMyLocationClickListener ${shouldMarkerFollowUserLocation.not()}")
                if (shouldMarkerFollowUserLocation.not()) {
                    shouldMarkerFollowUserLocation = true
                  // addTargetMarker(LatLng(it.latitude, it.longitude))
                }
            }
        }
    }

    private fun addTargetMarker(latLng: LatLng) {
        zoom = if (zoom == null || zoom!! < defaultZoom) {
            defaultZoom
        } else {
            map?.cameraPosition?.zoom
        }

        targetMarker?.remove()
        targetMarker= MapUtility.addMarkerAndMoveToSelectedLocation(requireContext(),map, latLng,zoom=zoom!!)
        locationViewModel.fetchLocationAddress(latLng)
    }

    private fun locationSource() = object : LocationSource {
        override fun deactivate() {
            stopLocationUpdate()
        }

        override fun activate(locationChangedListener: LocationSource.OnLocationChangedListener?) {
            this@LocationFragment.locationChangedListener = locationChangedListener
            locationUpdateStarted = true
//            getLastKnownLocation()
            startLocationUpdate()
        }
    }

    private fun locationCallback() =
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult.locations.isNullOrEmpty().not()) {
                    val location = locationResult.locations.last()
                    Timber.i("onLocationResult")
                   if (targetMarker == null || shouldMarkerFollowUserLocation) {
                      addTargetMarker(LatLng(location.latitude, location.longitude))
                    }
                    locationChangedListener?.onLocationChanged(location)
                    userLocation = location
                }
            }
        }

    private fun stopLocationUpdate() {
        fusedLocationClient?.removeLocationUpdates(locationCallback)
    }

    private fun startLocationUpdate() {
        if (locationRequest == null) {
            locationRequest = GpsUtility.createLocationRequest()
        }

        if (locationUpdateStarted) {
            fusedLocationClient?.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
        startLocationUpdate()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
        stopLocationUpdate()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mapView.onDestroy()
        stopLocationUpdate()
        fusedLocationClient = null
        activity?.apply {
            hideKeyboard(window)
        }
        if (gpsReceiver != null) {
            context?.apply {
                unregisterReceiver(gpsReceiver)
                gpsReceiver = null
            }
        }

    }

    inner class GpsLocationReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action?.equals(providerChanges) == true) {
                val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Timber.d("GPS provider is enabled")
                    map?.apply {
                        if (isMyLocationEnabled.not()) {
                            startLocationTracking(this)
                        } else {
                            this@LocationFragment.startLocationUpdate()
                        }
                    }
                } else {
                    Timber.d("GPS provider is disabled")
                    this@LocationFragment.stopLocationUpdate()
                    showErrorSnackbar(contentView, getString(R.string.wont_detect_location))
                }
            }
        }
    }
}