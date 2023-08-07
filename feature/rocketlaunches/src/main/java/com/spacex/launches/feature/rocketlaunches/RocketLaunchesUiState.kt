package com.spacex.launches.feature.rocketlaunches

import com.spacex.launches.core.model.data.RocketLaunch

/**
 * Sealed interface representing different states for Rocket Launches UI interactions.
 */
sealed interface RocketLaunchesUiState {
    object Empty : RocketLaunchesUiState
    object Loading : RocketLaunchesUiState
    data class Error(
        val rocketLaunches: List<RocketLaunch>?,
        val errorMessage: String,
    ) : RocketLaunchesUiState
    data class Success(
        val rocketLaunches: List<RocketLaunch>?,
    ) : RocketLaunchesUiState
}