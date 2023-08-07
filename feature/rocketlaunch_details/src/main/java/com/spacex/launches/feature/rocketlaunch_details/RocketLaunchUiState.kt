package com.spacex.launches.feature.rocketlaunch_details

import com.spacex.launches.core.model.data.RocketLaunch

/**
 * Sealed interface representing different states for Rocket Launch UI interactions.
 */
sealed interface RocketLaunchUiState {
    object Empty : RocketLaunchUiState
    object Loading : RocketLaunchUiState
    data class Error(
        val errorMessage: String,
    ) : RocketLaunchUiState
    data class Success(
        val rocketLaunch: RocketLaunch?,
    ) : RocketLaunchUiState
}