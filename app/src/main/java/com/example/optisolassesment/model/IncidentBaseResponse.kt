package com.example.optisolassesment.model

import com.google.gson.annotations.SerializedName

data class IncidentBaseResponse(
    @SerializedName("incidents")
    val incidents: List<Incidents>
)

data class Incidents(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("occurred_at")
    val occurred_at: Long,
    @SerializedName("updated_at")
    val updated_at: Long,
    @SerializedName("location_type")
    val location_type: String?,
    @SerializedName("location_description")
    val location_description: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("type_properties")
    val type_properties: String?,
    @SerializedName("media")
    val media: Media

)

data class Media(
    @SerializedName("image_url")
    val image_url: String?,
    @SerializedName("image_url_thumb")
    val image_url_thumb: String?
)
