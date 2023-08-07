package com.spacex.launches.core.network

import com.spacex.launches.core.network.model.NetworkRocketLaunch
import retrofit2.http.GET

interface SpaceXNetworkApi {

    /**
     * Retrieve list of rocket launches from the SpaceX API.
     */
    @GET("launches")
    suspend fun getRocketLaunches(): List<NetworkRocketLaunch>

    companion object {
        const val SpaceXBaseUrl = "https://api.spacexdata.com/v4/"
    }
}