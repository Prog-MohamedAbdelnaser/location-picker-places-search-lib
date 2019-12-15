package com.softartch_lib.locationpicker

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.*
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.LocationSource
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.libraries.places.compat.AutocompleteFilter
import com.google.android.libraries.places.compat.Place
import com.google.android.libraries.places.compat.ui.PlaceAutocomplete
import com.softartch_lib.R
import com.softartch_lib.component.RequestDataState
import com.softartch_lib.component.fragment.BaseFragment
import com.softartch_lib.exceptions.EnableLocationServicetException
import com.softartch_lib.exceptions.PermissionDeniedException
import com.softartch_lib.utility.hideKeyboard
import com.softtech.android_structure.base.utility.GpsUtility
import com.softtech.android_structure.base.utility.MapUtility
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.SingleSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


abstract class LocationPickerFragment2 : BaseFragment(), OnMapReadyCallback {

    companion object {

        const val providerChanges = "android.location.PROVIDERS_CHANGED"
        const val defaultZoom=15f

        const val PLACE_REQUEST_CODE = 1002

    }

    fun setSearchCountryFilter(country:String){
        this.country=country
    }
    private var country:String=""
    abstract fun mapViewResource(): MapView

    private var zoom:Float?= null

    private val locationPickerViewModel : LocationPickerViewModel by viewModel()

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

    private var requestLocationSettingEmitter:SingleEmitter<Boolean>? = null

    private var disposables= CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.savedInstanceState=savedInstanceState
    }

    override fun onViewInflated(parentView: View, childView: View) {
        super.onViewInflated(parentView, childView)

        mapViewResource().onCreate(savedInstanceState)
        mapViewResource().getMapAsync(this)
        initObservers()

    }

    private fun initObservers() {
        locationPickerViewModel.apply {
            locationAddressLiveDataState.observe(
                this@LocationPickerFragment2, Observer {
                handleLocationAddressState(it)
            })
        }
    }

    private fun handleLocationAddressState(state: RequestDataState<LocationAddress>?) {
        when(state){
            is RequestDataState.LoadingShow->{
                setMarkerTitle("")
            }
            is RequestDataState.Success->{
                Log.i("Location","handleLocationAddressState ${state.data}")
                state.data.addressName?.let { setMarkerTitle(it) }
                onGetLocationAddress(state.data)
            }
            is RequestDataState.Error->{
               // state.exception.message.let { showErrorSnackbar(view!!, it) }
            }
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        println("onMapReady")
        this.map=map
        map!!.mapType = GoogleMap.MAP_TYPE_TERRAIN
        setupGoogleMap(map)

        checkPermission()

    }

    fun setMarkerTitle(title:String){
        tvAddress?.text = title
        targetMarker?.showInfoWindow()
    }



    private fun checkPermission(){

        Log.d("TASKLOC", "checkPermission")

        val rxPermissions = RxPermissions(this)
        disposables.add(
            rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
                .doOnNext { granted ->
                    if (!granted) {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                            Timber.i("permission try again$granted")
                            Log.d("TASKLOC", "checkPermission")
                           checkPermission()
                        } else {
                            Log.d("TASKLOC", "PopupPermission")

                            // PopupPermission(requireContext()).show()
                        }
                    } else {
                        listenToGPSChanges()
                        Timber.i("permission granted$granted")
                    }

                }.subscribe({
                    GpsUtility.requsetEnableLocationServiceSetting(requireContext())
                        .subscribe({
                            startLocationTracking(map!!)

                        },{

                            it.printStackTrace()
                        })
                },{
                    it.printStackTrace()
                }))


    }



    private fun listenToGPSChanges() {
        requireActivity().apply {
            gpsReceiver = GpsLocationReceiver()
           registerReceiver(gpsReceiver, IntentFilter(providerChanges))
       }
    }

    private fun setupGoogleMap(googleMap: GoogleMap) {
        googleMap.apply {
            uiSettings.setAllGesturesEnabled(true)
            uiSettings.isMyLocationButtonEnabled=true
            uiSettings.isZoomControlsEnabled=true

            setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
                @SuppressLint("InflateParams")
                override fun getInfoContents(p0: Marker?): View? {
                    if (tvAddress == null) {
                        tvAddress = LayoutInflater
                            .from(context)
                            .inflate(com.softartch_lib.R.layout.location_address, null, false)
                                as AppCompatTextView?
                    }
                    return tvAddress
                }

                override fun getInfoWindow(p0: Marker?): View? = null

            })



            setOnMapClickListener {
                Log.i("Location","setOnMapClickListener$it")
                shouldMarkerFollowUserLocation = false
                addTargetMarker(it,moveCamera = false)
            }


            setOnCameraIdleListener {
                Log.i("Location","setOnCameraIdleListener ${cameraPosition.target.latitude} , ${cameraPosition.target.longitude}")

                if (targetMarker != null) {
                    locationPickerViewModel.onCameraIdle(LatLng(
                        cameraPosition.target.latitude,
                        cameraPosition.target.longitude)
                    )
                }
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
                    Log.i("Location","setOnMyLocationButtonClickListener $latLng")

                    if (targetMarker == null || shouldMarkerFollowUserLocation.not()) {
                        //targetAccuracy = accuracy
                        shouldMarkerFollowUserLocation = true
                        addTargetMarker(latLng)
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
                   addTargetMarker(LatLng(it.latitude, it.longitude))
                }
            }
        }
    }

    private fun addTargetMarker(latLng: LatLng,address:String?=null,moveCamera:Boolean=true) {
        zoom = if (zoom == null || zoom!! < defaultZoom) {
            defaultZoom
        } else {
            map?.cameraPosition?.zoom
        }

        targetMarker?.remove()
        targetMarker=  if (moveCamera)
         MapUtility.addMarkerAndMoveToSelectedLocation(requireContext(),map, latLng,zoom=zoom!!)
        else
             MapUtility.addMarkerAndMoveToSelectedLocation(requireContext(),map, latLng,zoom = zoom!!)
        locationPickerViewModel.setTargetLocationAddress(LocationAddress(latLng!!.latitude!!,latLng!!.longitude,address))

    }

    private fun locationSource() = object : LocationSource {
        override fun deactivate() {
            stopLocationUpdate()
        }

        override fun activate(locationChangedListener: LocationSource.OnLocationChangedListener?) {
            this@LocationPickerFragment2.locationChangedListener = locationChangedListener
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
                      addTargetMarker(LatLng(location.latitude, location.longitude),moveCamera = true)
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
        mapViewResource().onStart()
        startLocationUpdate()
    }

    override fun onResume() {
        super.onResume()
        mapViewResource().onResume()
    }

    override fun onStop() {
        super.onStop()
        mapViewResource().onStop()
        stopLocationUpdate()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mapViewResource().onDestroy()
        stopLocationUpdate()
        if (!disposables.isDisposed)
            disposables.dispose()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){

            PLACE_REQUEST_CODE->{
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        val place: Place = PlaceAutocomplete.getPlace(activity, data)
                        shouldMarkerFollowUserLocation = false

                        addTargetMarker( place.latLng, place.address.toString())
                        onGetLocationAddress(LocationAddress(place.latLng.latitude,place.latLng.latitude,place.address.toString()))
                    }
                    PlaceAutocomplete.RESULT_ERROR -> {
                        val status: Status = PlaceAutocomplete.getStatus(activity, data)
                    }

                }
        }
    }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.map_search, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item?.itemId == R.id.search) {
            try {
                val intent: Intent = PlaceAutocomplete
                    .IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                    .setFilter(createAutocompleteFilter())
                    .build(requireActivity())
                startActivityForResult(intent, PLACE_REQUEST_CODE)
            } catch (e: GooglePlayServicesRepairableException) {
                e.printStackTrace()
            } catch (e: GooglePlayServicesNotAvailableException) {
                e.printStackTrace()
            }

        }
        return super.onOptionsItemSelected(item)

    }

    private fun createAutocompleteFilter(): AutocompleteFilter = AutocompleteFilter.Builder()
        .setCountry(country)
        .build()

    open fun onGetLocationAddress(locationAddress: LocationAddress) {}


    inner class GpsLocationReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            Log.w("GpsLocationReceiver", "onReceive ${intent.action}")

            if (intent.action?.equals(providerChanges) == true) {
                val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Timber.d("GPS provider is enabled")
                    map?.apply {
                        if (isMyLocationEnabled.not()) {
                            startLocationTracking(this)
                        } else {
                            this@LocationPickerFragment2.startLocationUpdate()
                        }
                    }
                } else {
                    Timber.d("GPS provider is disabled")
                    this@LocationPickerFragment2.stopLocationUpdate()

                 //   showErrorSnackbar(contentView, getString(R.string.wont_detect_location))
                }
            }
        }
    }


}