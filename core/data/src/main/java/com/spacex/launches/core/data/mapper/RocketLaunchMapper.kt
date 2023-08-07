package com.spacex.launches.core.data.mapper

import com.spacex.launches.core.database.model.RocketLaunchEntity
import com.spacex.launches.core.model.data.RocketLaunch
import com.spacex.launches.core.network.model.NetworkRocketLaunch


/**
 * Converts the [RocketLaunchEntity] to a [RocketLaunch] (external model) object.
 */
fun RocketLaunchEntity.asExternalModel() = RocketLaunch(
    flightNumber = flightNumber,
    name = name,
    dateUTC = dateUTC,
    details = details,
    success = success,
    upcoming = upcoming
)

/**
 * Converts the [NetworkRocketLaunch] to a [RocketLaunchEntity] (local entity) object.
 */
fun NetworkRocketLaunch.asEntity() = RocketLaunchEntity(
    flightNumber = flightNumber,
    name = name,
    dateUTC = dateUtc,
    details = details,
    success = success,
    upcoming = upcoming
)
