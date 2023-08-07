/**
 * Repository implementation for SpaceX data.
 */
package com.spacex.launches.core.data.repository

import androidx.room.withTransaction
import com.spacex.launches.core.common.util.Resource
import com.spacex.launches.core.data.mapper.asEntity
import com.spacex.launches.core.data.mapper.asExternalModel
import com.spacex.launches.core.database.SpaceXDatabase
import com.spacex.launches.core.model.data.RocketLaunch
import com.spacex.launches.core.network.SpaceXNetworkApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SpaceXRepositoryImpl @Inject constructor(
    private val database: SpaceXDatabase,
    private val api: SpaceXNetworkApi,
) : SpaceXRepository {

    private val dao = database.rocketLaunchDao()

    /**
     * Retrieves a flow of rocket launches.
     */
    override fun getRocketLaunches(): Flow<Resource<List<RocketLaunch>>> = flow {
        emit(Resource.Loading()) // Emit loading state.

        val cachedRocketLaunches = dao.getRocketLaunches().map { it.asExternalModel() }
        emit(Resource.Success(cachedRocketLaunches)) // Emit cached data.

        val networkRocketLaunchesResult = fetchAndInsertRocketLaunches() // Fetch data from the network.

        // Emit data from the network if successful, otherwise emit an error state with cached data.
        if (networkRocketLaunchesResult is Resource.Success) {
            emit(Resource.Success(networkRocketLaunchesResult.data))
        } else if (networkRocketLaunchesResult is Resource.Error) {
            emit(Resource.Error(
                message = networkRocketLaunchesResult.message ?: "Unknown error",
                data = cachedRocketLaunches
            ))
        }
    }


    /**
     * Retrieves a flow of a specific rocket launch by flight number.
     *
     * @param flightNumber The flight number of the rocket launch.
     */
    override fun getRocketLaunch(flightNumber: Int): Flow<Resource<RocketLaunch>> = flow {
        emit(Resource.Loading()) // Emit loading state.

        // Emit cached data if present, otherwise make network request
        val cachedRocketLaunch = dao.getRocketLaunch(flightNumber)?.asExternalModel()
        if (cachedRocketLaunch != null) {
            emit(Resource.Success(cachedRocketLaunch))
        } else {
            val networkRocketLaunchesResult = fetchAndInsertRocketLaunches()

            // Emit data from the network if successful, otherwise emit an error state with cached data.
            if (networkRocketLaunchesResult is Resource.Success) {
                val updatedRocketLaunch = dao.getRocketLaunch(flightNumber)?.asExternalModel()
                emit(Resource.Success(updatedRocketLaunch))
            } else if (networkRocketLaunchesResult is Resource.Error) {
                emit(Resource.Error(message = networkRocketLaunchesResult.message ?: "Unknown error"))
            }
        }
    }

    /**
     * Fetches rocket launches from the network and updates the database.
     */
    private suspend fun fetchAndInsertRocketLaunches(): Resource<List<RocketLaunch>> {
        try {
            val networkRocketLaunches = api.getRocketLaunches()
            database.withTransaction {
                dao.deleteLaunches()
                dao.insertOrIgnoreRocketLaunches(networkRocketLaunches.map { it.asEntity() })
            }
            val updatedRocketLaunches = dao.getRocketLaunches().map { it.asExternalModel() }
            return Resource.Success(updatedRocketLaunches)
        } catch (e: HttpException) {
            val errorMessage = "Couldn't reach server. Please check the internet connection"
            return Resource.Error(message = errorMessage)
        } catch (e: IOException) {
            val errorMessage = "Couldn't load rocket launch data"
            return Resource.Error(message = e.localizedMessage ?: errorMessage)
        } catch (e: Exception) {
            val errorMessage = "Unknown error"
            return Resource.Error(message = e.localizedMessage ?: errorMessage)
        }
    }
}

