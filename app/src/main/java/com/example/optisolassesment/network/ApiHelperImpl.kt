package com.example.optisolassesment.network

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {
    override suspend fun getIncidents(pageNumber: Int, perPage: Int) =
        apiService.getIncidents(page = pageNumber, per_page = perPage)

    override suspend fun getLocations(limit: Int) = apiService.getLocations(limit)
}