package com.example.optisolassesment.model

import com.google.gson.annotations.SerializedName

data class DistanceResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("error_message")
    val error_message: String?,
    @SerializedName("origin_addresses")
    val origin_addresses: List<String>,
    @SerializedName("destination_addresses")
    val destination_addresses: List<String>,
    @SerializedName("rows")
    val rows: List<Rows>

)

data class Rows(@SerializedName("elements") val element: List<Elements>)

data class Elements(
    @SerializedName("status") val status: String,
    @SerializedName("duration") val duration: DurationOrDist,
    @SerializedName("distance") val distance: DurationOrDist
)

data class DurationOrDist(
    @SerializedName("value") val value: Int,
    @SerializedName("text") val text: String
)
