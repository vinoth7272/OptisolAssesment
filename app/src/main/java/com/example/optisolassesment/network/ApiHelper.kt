package com.example.optisolassesment.network

import com.example.optisolassesment.model.IncidentBaseResponse
import com.example.optisolassesment.model.LocationBaseResponse
import retrofit2.Response

interface ApiHelper {
    suspend fun getIncidents(pageNumber: Int,perPage : Int): Response<IncidentBaseResponse>
    suspend fun getLocations(limit:Int): Response<LocationBaseResponse>
}