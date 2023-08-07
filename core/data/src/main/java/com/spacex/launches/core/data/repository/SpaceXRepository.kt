package com.spacex.launches.core.data.repository

import com.spacex.launches.core.common.util.Resource
import com.spacex.launches.core.model.data.RocketLaunch
import kotlinx.coroutines.flow.Flow

/**
 * Interface defining SpaceXRepository
 */
interface SpaceXRepository {

    /**
     * Fetches a Flow of Resource with a list of [RocketLaunch] objects.
     * This function represents an asynchronous data stream for getting all Rocket Launches.
     *
     * @return Flow of Resource wrapping a list of [RocketLaunch] objects.
     */
    fun getRocketLaunches(): Flow<Resource<List<RocketLaunch>>>

    /**
     * Fetches a Flow of Resource with a single [RocketLaunch] object corresponding to the given flight number.
     * This function represents an asynchronous data stream for getting a specific Rocket Launch by its flight number.
     *
     * @param flightNumber The flight number of the Rocket Launch to fetch.
     * @return Flow of Resource wrapping a single RocketLaunch object.
     */
    fun getRocketLaunch(flightNumber: Int): Flow<Resource<RocketLaunch>>
}