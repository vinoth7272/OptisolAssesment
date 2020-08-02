package com.example.optisolassesment.viewmodel

import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.optisolassesment.model.IncidentBaseResponse
import com.example.optisolassesment.model.LocationBaseResponse
import com.example.optisolassesment.network.Resource
import com.example.optisolassesment.utils.Constant.INCIDENT_PAGE_SIZE
import com.example.optisolassesment.utils.Constant.LOCATION_PAGE_LIMIT
import com.example.optisolassesment.utils.Constant.incidentPageNumber
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch


class SharedViewModel(private val dataRepository: DataRepository) : ViewModel() {

    val incidentResponseLiveData = MutableLiveData<Resource<IncidentBaseResponse>>()
    val locationResponseLiveData = MutableLiveData<Resource<LocationBaseResponse>>()
    var isFirstPage = true

    fun fetchIncidentData() {
        incidentResponseLiveData.postValue(Resource.loading(null))
        try {
            viewModelScope.launch {
                Log.d("OPTISOL", "$incidentPageNumber")
                val incidentDataResource =
                    dataRepository.fetchIncidents(incidentPageNumber + 1, INCIDENT_PAGE_SIZE)
                incidentResponseLiveData.postValue(incidentDataResource)
            }
        } catch (e: Exception) {
            incidentResponseLiveData.postValue(Resource.error(e.localizedMessage, null))
        }
    }

    fun fetchLocationsData() {
        locationResponseLiveData.postValue(Resource.loading(null))
        try {
            viewModelScope.launch {
                val locationDataResource =
                    dataRepository.fetchLocations(LOCATION_PAGE_LIMIT)
                locationResponseLiveData.postValue(locationDataResource)
            }
        } catch (e: Exception) {
            locationResponseLiveData.postValue(Resource.error(e.localizedMessage, null))
        }
    }

}