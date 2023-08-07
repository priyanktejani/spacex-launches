package com.spacex.launches.core.network.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Model object to store data about a network rocket launch.
 */
@JsonClass(generateAdapter = true)
data class NetworkRocketLaunch(
    @Json(name = "flight_number")
    val flightNumber: Int,
    val name: String,
    @Json(name = "date_utc")
    val dateUtc: String,
    val details: String?,
    val success: Boolean?,
    val upcoming: Boolean?
)