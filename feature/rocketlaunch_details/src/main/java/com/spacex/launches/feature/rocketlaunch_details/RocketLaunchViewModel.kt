package com.spacex.launches.feature.rocketlaunch_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spacex.launches.core.common.util.Resource
import com.spacex.launches.core.data.repository.SpaceXRepository
import com.spacex.launches.core.model.data.RocketLaunch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel class to expose data to the ListFragment that it is required to display.
 */

@HiltViewModel
class RocketLaunchViewModel @Inject constructor(
    private val repository: SpaceXRepository
) : ViewModel() {

    private val _state: MutableStateFlow<RocketLaunchUiState> =
        MutableStateFlow(RocketLaunchUiState.Loading)
    val state: StateFlow<RocketLaunchUiState> = _state

    /**
     * Request the [RocketLaunch] data.
     */
    fun retrieveData(flightNumber: Int) {
        viewModelScope.launch {
            repository.getRocketLaunch(flightNumber)
                .onStart {
                    _state.value = RocketLaunchUiState.Loading
                }
                .catch { exception ->
                    _state.value =
                        RocketLaunchUiState.Error(exception.message ?: "Unknown error")
                }
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> RocketLaunchUiState.Loading
                        is Resource.Error -> RocketLaunchUiState.Error(
                            result.message ?: "Unknown error"
                        )
                        is Resource.Success -> {
                            if (result.data == null)
                                RocketLaunchUiState.Empty
                            else
                               RocketLaunchUiState.Success(result.data)
                        }
                    }.apply {
                        _state.value = this
                    }
                }
        }
    }
}
