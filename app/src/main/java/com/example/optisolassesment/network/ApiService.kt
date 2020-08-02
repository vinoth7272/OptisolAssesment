package com.example.optisolassesment.network

import com.example.optisolassesment.model.IncidentBaseResponse
import com.example.optisolassesment.model.LocationBaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("incidents?")
    suspend fun getIncidents(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = 15
    ): Response<IncidentBaseResponse>

    @GET("locations?")
    suspend fun getLocations(
        @Query("limit") page: Int = 15
    ): Response<LocationBaseResponse>
}