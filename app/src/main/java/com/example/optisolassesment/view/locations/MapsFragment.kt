package com.example.optisolassesment.view.locations

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.optisolassesment.R
import com.example.optisolassesment.model.Incidents
import com.example.optisolassesment.model.LocationFeature
import com.example.optisolassesment.utils.Constant.LOCATION_PERMISSION_REQUEST_CODE
import com.example.optisolassesment.utils.DataUtil.getIncidentData
import com.example.optisolassesment.utils.PermissionUtils
import com.example.optisolassesment.utils.distanceBetween
import com.example.optisolassesment.view.MainActivity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.maps_fragment.*


class MapsFragment : Fragment(), OnMapReadyCallback {

    private var incidentDetail: Incidents? = null
    private lateinit var theftLoc: LatLng
    private lateinit var fragmentContext: Context
    private lateinit var mMap: GoogleMap
    private lateinit var locationFeature: LocationFeature


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        bundle?.let {
            locationFeature = it.getParcelable("LOCATION_ITEM")!!
            Log.d("TAG", locationFeature.toString())
        }
        incidentDetail = getIncidentData(locationFeature.properties.id)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.maps_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpMap()
        setUpActionBar()
    }

    private fun setUpMap() {
        activity?.let {
            val mapFragment: SupportMapFragment? =
                childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
            mapFragment?.getMapAsync(this)
        }
    }

    private fun setUpActionBar() {
        if ((activity as MainActivity).supportActionBar != null) {
            val actionBar = (activity as MainActivity).supportActionBar
            incidentDetail?.let {
                actionBar?.title = incidentDetail?.title
            }
            actionBar!!.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeButtonEnabled(true)
        }
    }

    override fun onMapReady(googleMaps: GoogleMap) {
        mMap = googleMaps
        theftLoc =
            LatLng(locationFeature.geometry.coordinates[0], locationFeature.geometry.coordinates[1])
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(theftLoc, 8f))
        mMap.addMarker(MarkerOptions().position(theftLoc))

    }


    override fun onStart() {
        super.onStart()
        when {
            PermissionUtils.isAccessFineLocationGranted(fragmentContext) -> {
                checkIsLocationEnabled()
            }
            else -> {
                PermissionUtils.requestAccessFineLocationPermission(
                    activity,
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    private fun setUpLocationListener() {
        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(fragmentContext)
        // for getting the current location update after every 60 seconds with high accuracy
        val locationRequest = LocationRequest().setInterval(60 * 1000).setFastestInterval(60 * 1000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        if (ActivityCompat.checkSelfPermission(
                fragmentContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                fragmentContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                @SuppressLint("SetTextI18n")
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    for (location in locationResult.locations) {
                        val currentLatLong = LatLng(location.latitude, location.longitude)
                        theftLoc =
                            LatLng(locationFeature.geometry.coordinates[0], locationFeature.geometry.coordinates[1])
                        val distance = distanceBetween(
                            theftLoc,
                            currentLatLong
                        )
                        txt_distance?.let {
                            it.text = "Distance : $distance KM"
                            progressBar.visibility = View.GONE
                        }

                    }
                }
            },
            Looper.myLooper()
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkIsLocationEnabled()
                } else {
                    Toast.makeText(
                        fragmentContext,
                        getString(R.string.location_permission_not_granted),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun checkIsLocationEnabled() {
        when {
            PermissionUtils.isLocationEnabled(fragmentContext) -> {
                setUpLocationListener()
            }
            else -> {
                PermissionUtils.showGPSNotEnabledDialog(fragmentContext)
            }
        }
    }
}