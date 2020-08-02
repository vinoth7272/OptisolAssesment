package com.example.optisolassesment.viewmodel

import android.util.Log
import com.example.optisolassesment.model.DistanceResponse
import com.example.optisolassesment.model.IncidentBaseResponse
import com.example.optisolassesment.model.LocationBaseResponse
import com.example.optisolassesment.network.ApiHelper
import com.example.optisolassesment.network.MapApiService
import com.example.optisolassesment.network.Resource
import com.example.optisolassesment.utils.Constant.incidentPageNumber

class DataRepository(
    private val apiHelper: ApiHelper,
    private val mapApiService: MapApiService
) {

    suspend fun fetchIncidents(page: Int, pageSize: Int): Resource<IncidentBaseResponse> {
        val incidentBaseResponse: Resource<IncidentBaseResponse>
        val response = apiHelper.getIncidents(page, pageSize)
        incidentBaseResponse = if (response.isSuccessful) {
            val responseBody = response.body() as IncidentBaseResponse
            incidentPageNumber = page
            Resource.success(responseBody)
        } else {
            Resource.error(response.message(), null)
        }
        return incidentBaseResponse
    }

    suspend fun fetchLocations(limit: Int): Resource<LocationBaseResponse> {
        val locationBaseResponse: Resource<LocationBaseResponse>
        val response = apiHelper.getLocations(limit)
        locationBaseResponse = if (response.isSuccessful) {
            val responseBody = response.body() as LocationBaseResponse
            Resource.success(responseBody)
        } else {
            Resource.error(response.message(), null)
        }
        return locationBaseResponse
    }

    suspend fun fetchLocationDistance(origin: String, dest: String): Resource<DistanceResponse> {
        val distanceResponse : Resource<DistanceResponse>
        val response = mapApiService.getDistance(origin,dest)
        Log.d("Result","${response}")
        distanceResponse = if (response.isSuccessful) {
            val responseBody = response.body() as DistanceResponse
            if(responseBody.status=="OK"){
                Log.d("Result1","${responseBody.status}")
                Resource.success(responseBody)
            }else{
                Log.d("Result2","$${responseBody.status}")
                Resource.error(responseBody.error_message, null)
            }
        } else {
            Log.d("Result3","${response.message()}")
            Resource.error(response.message(), null)
        }
        return distanceResponse
    }

}
