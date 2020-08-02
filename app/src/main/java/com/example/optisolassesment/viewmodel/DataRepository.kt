package com.example.optisolassesment.viewmodel

import com.example.optisolassesment.model.IncidentBaseResponse
import com.example.optisolassesment.model.LocationBaseResponse
import com.example.optisolassesment.network.ApiHelper
import com.example.optisolassesment.network.Resource
import com.example.optisolassesment.utils.Constant
import com.example.optisolassesment.utils.Constant.incidentPageNumber

class DataRepository(private val apiHelper: ApiHelper) {

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

}
