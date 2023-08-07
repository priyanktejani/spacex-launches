package com.spacex.launches.core.data.repository

import com.spacex.launches.core.common.util.Resource
import com.spacex.launches.core.model.data.RocketLaunch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeSpaceXRepository : SpaceXRepository {

    private val rocketLaunchesFlow = mutableListOf<RocketLaunch>()
    private var errorMessage: String = ""

    private var shouldEmitLoadingState: Boolean = false
    private var shouldEmitSuccessState: Boolean = false
    private var shouldEmitErrorState: Boolean = false


    /**
     * Sets whether the repository should emit the loading state or not.
     *
     * @param emitLoadingState True to emit the loading state, false otherwise.
     */
    fun setLoadingState(emitLoadingState: Boolean) {
        shouldEmitLoadingState = emitLoadingState
    }

    /**
     * Sets whether the repository should emit the error state or not.
     *
     * @param emitErrorState True to emit the error state, false otherwise.
     */
    fun setErrorState(emitErrorState: Boolean) {
        shouldEmitErrorState = emitErrorState
    }

    /**
     * Sets whether the repository should emit the success state or not.
     *
     * @param emitSuccessState True to emit the success state, false otherwise.
     */
    fun setSuccessState(emitSuccessState: Boolean) {
        shouldEmitSuccessState = emitSuccessState
    }

    /**
     * Sets the rocket launches data in the fake repository.
     *
     * @param rocketLaunches The list of rocket launches to set.
     */
    fun setRocketLaunches(rocketLaunches: List<RocketLaunch>) {
        rocketLaunchesFlow.clear()
        rocketLaunchesFlow.addAll(rocketLaunches)
    }

    /**
     * Sets an error state in the fake repository.
     *
     * @param errorMessage The error message to set.
     *
     * @param rocketLaunches The list of rocket launches to set.
     */
    fun setError(errorMessage: String, rocketLaunches: List<RocketLaunch>?) {
        rocketLaunchesFlow.clear()
        this.errorMessage = errorMessage
        if (rocketLaunches != null) rocketLaunchesFlow.addAll(rocketLaunches)
    }

    /**
     * Retrieves a flow of rocket launches from the fake repository.
     */
    override fun getRocketLaunches(): Flow<Resource<List<RocketLaunch>>> = flow {
        if (shouldEmitLoadingState) {
            emit(Resource.Loading())
        }
        if (shouldEmitSuccessState) {
            val rocketLaunches = rocketLaunchesFlow.toList()
            emit(Resource.Success(rocketLaunches))
        }
        if (shouldEmitErrorState) {
            val errorMessage = errorMessage
            val rocketLaunches = rocketLaunchesFlow.toList()
            emit(Resource.Error(errorMessage, rocketLaunches))
        }
    }

    /**
     * Retrieves a flow of a specific rocket launch by flight number from the fake repository.
     *
     * @param flightNumber The flight number of the rocket launch.
     */
    override fun getRocketLaunch(flightNumber: Int): Flow<Resource<RocketLaunch>>  = flow {
        if (shouldEmitLoadingState) {
            emit(Resource.Loading())
        }
        if (shouldEmitSuccessState) {
            val rocketLaunch = rocketLaunchesFlow.find { it.flightNumber == flightNumber }
            emit(Resource.Success(rocketLaunch))
        }
        if (shouldEmitErrorState) {
            val errorMessage = errorMessage
            val rocketLaunch = rocketLaunchesFlow.find { it.flightNumber == flightNumber }
            emit(Resource.Error(errorMessage, rocketLaunch))
        }
    }
}

