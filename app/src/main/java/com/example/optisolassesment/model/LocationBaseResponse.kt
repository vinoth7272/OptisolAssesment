package com.example.optisolassesment.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class LocationBaseResponse(
    @SerializedName("type") val typeName: String,
    @SerializedName("features") val featuresList: List<LocationFeature>
)

@Parcelize
data class LocationFeature (
    @SerializedName("type") val featureType: String,
    @SerializedName("properties") val properties: Property,
    @SerializedName("geometry") val geometry: Geometry
):Parcelable

@Parcelize
data class Property(
    @SerializedName("id") val id: String,
    @SerializedName("type") val propertyType: String,
    @SerializedName("occurred_at") val occurred_at: Long
):Parcelable

@Parcelize
data class Geometry(
    @SerializedName("type") val geometryType: String,
    @SerializedName("coordinates") val coordinates: List<Double>
):Parcelable

