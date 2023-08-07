package com.spacex.launches.core.model.data


/**
 * Model object to store data about a rocket launch.
 */

data class RocketLaunch(
    val flightNumber: Int,
    val name: String,
    val dateUTC: String,
    val details: String?,
    val success: Boolean?,
    val upcoming: Boolean?
)