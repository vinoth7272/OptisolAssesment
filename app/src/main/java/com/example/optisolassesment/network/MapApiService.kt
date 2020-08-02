package com.example.optisolassesment.network

import com.example.optisolassesment.model.DistanceResponse
import com.example.optisolassesment.model.LocationBaseResponse
import com.example.optisolassesment.utils.Constant.MAP_API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MapApiService {

    @GET("maps/api/distancematrix/json?")
    suspend fun getDistance(
        @Query("origins") origin: String,
        @Query("destinations") destinations: String ,
        @Query("key") key: String = MAP_API_KEY
    ): Response<DistanceResponse>
}