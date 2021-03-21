package com.brsan7.recursosnativos.localizacao

import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.brsan7.recursosnativos.R
import com.brsan7.recursosnativos.ToolBarActivitys
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MapsLocationActivity : ToolBarActivitys(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private var locationUpdateState = false

    companion object{
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val REQUEST_CHECK_SETTINGS = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps_location)
        setupToolBar(toolBar, getString(R.string.toolBarTitleLocalizacao), true)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationCallback = object : LocationCallback(){
            override fun onLocationResult(p0: LocationResult?) {
                super.onLocationResult(p0)
                lastLocation = p0!!.lastLocation
                placeMarkerOnMap(LatLng(lastLocation.latitude, lastLocation.longitude))
            }
        }
        createLocationRequest()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.getUiSettings().setZoomControlsEnabled(true)
        mMap.setOnMarkerClickListener(this)
        setUpMap()
    }

    private fun setUpMap(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        mMap.isMyLocationEnabled = true

        mMap.mapType = GoogleMap.MAP_TYPE_HYBRID

        fusedLocationClient.lastLocation.addOnSuccessListener(this) {location ->
            if(location != null){
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap(currentLatLng)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 7.0F))
            }
        }
    }

    private fun placeMarkerOnMap(location: LatLng){
        val markerOptions = MarkerOptions().position(location)
        val titleStr = getAddress(location)
        markerOptions.title(titleStr)
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(
                BitmapFactory.decodeResource(resources, R.mipmap.ic_user_location)))
        mMap.addMarker(markerOptions)
    }

    private fun getAddress(latLng: LatLng): String{
        val geocoder: Geocoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address>
        = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

        val address = addresses[0].getAddressLine(0)
        /*val city = addresses[0].locality
        val state = addresses[0].adminArea
        val country = addresses[0].countryName
        val postalCode = addresses[0].postalCode*/
        return address
    }

    private fun startLocationUpdates(){
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun createLocationRequest(){
        locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val cliente = LocationServices.getSettingsClient(this)
        val task = cliente.checkLocationSettings(builder.build())

        task.addOnSuccessListener { e ->
            if(e is ResolvableApiException){
                try{
                    e.startResolutionForResult(this@MapsLocationActivity, REQUEST_CHECK_SETTINGS)
                }
                catch (sendEx: IntentSender.SendIntentException){}
            }
        }
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
        locationUpdateState = false
    }

    override fun onResume() {
        super.onResume()
        if(!locationUpdateState){
            startLocationUpdates()
            locationUpdateState = true
        }
    }

    override fun onMarkerClick(p0: Marker?) = false
}