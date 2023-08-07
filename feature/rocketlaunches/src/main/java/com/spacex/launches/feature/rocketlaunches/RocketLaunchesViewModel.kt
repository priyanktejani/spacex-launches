package com.spacex.launches.feature.rocketlaunches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spacex.launches.core.common.util.Resource
import com.spacex.launches.core.data.repository.SpaceXRepository
import com.spacex.launches.core.model.data.RocketLaunch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

/**
 * ViewModel class to expose data to the ListFragment that it is required to display.
 */

@HiltViewModel
class RocketLaunchesViewModel @Inject constructor(
    private val repository: SpaceXRepository
) : ViewModel() {

    private val _state: MutableStateFlow<RocketLaunchesUiState> =
        MutableStateFlow(RocketLaunchesUiState.Empty)
    val state: StateFlow<RocketLaunchesUiState> = _state

    /**
     * Request the list of [RocketLaunch] data.
     */
    fun retrieveData() {
        repository.getRocketLaunches()
            .onStart {
                _state.value = RocketLaunchesUiState.Loading
            }
            .map { result ->
                when (result) {
                    is Resource.Loading -> RocketLaunchesUiState.Loading
                    is Resource.Error -> RocketLaunchesUiState.Error(
                        result.data,
                        result.message ?: "Unknown error"
                    )
                    is Resource.Success -> {
                        if (result.data.isNullOrEmpty()) RocketLaunchesUiState.Empty
                        else RocketLaunchesUiState.Success(result.data)
                    }
                }
            }
            .catch { exception ->
                RocketLaunchesUiState.Error(null, exception.message ?: "Unknown error")
            }
            .onEach { state ->
                _state.value = state
            }
            .launchIn(viewModelScope)
    }
}
