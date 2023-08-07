package com.spacex.launches.feature.rocketlaunch_details

import com.spacex.launches.core.data.repository.FakeSpaceXRepository
import com.spacex.launches.core.model.data.RocketLaunch
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RocketLaunchViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var repository: FakeSpaceXRepository
    private lateinit var viewModel: RocketLaunchViewModel

    private val rocketLaunch =
        RocketLaunch(1, "Launch 1", "2023-07-01", "Details 1", success = true, upcoming = false)

    private val rocketLaunches = listOf(
        RocketLaunch(1, "Launch 1", "2023-07-01", "Details 1", success = true, upcoming = false),
        RocketLaunch(2, "Launch 2", "2023-07-02", "Details 2", success = false, upcoming = true),
        RocketLaunch(3, "Launch 3", "2023-07-03", "Details 3", success = false, upcoming = false)
    )

    @Before
    fun setUp() {
        repository = FakeSpaceXRepository()
        viewModel = RocketLaunchViewModel(repository = repository)
    }

    @Test
    fun retrieveData_loadingState_emitsLoading() = runTest {
        // Act
        viewModel.retrieveData(rocketLaunch.flightNumber)
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect()
        }

        // Assert
        assertTrue(viewModel.state.value is RocketLaunchUiState.Loading)
        assertEquals(RocketLaunchUiState.Loading, viewModel.state.value)
        collectJob.cancel()
    }

    @Test
    fun retrieveData_emptyResult_emitsEmptyState() = runTest {
        // Arrange
        repository.setLoadingState(true)
        repository.setSuccessState(true)

        // Act
        viewModel.retrieveData(rocketLaunch.flightNumber)
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect()
        }

        // Assert
        assertTrue(viewModel.state.value is RocketLaunchUiState.Empty)
        assertEquals(RocketLaunchUiState.Empty, viewModel.state.value)
        collectJob.cancel()
    }

    @Test
    fun retrieveData_errorResult_emitsErrorState() = runTest {
        // Arrange
        val errorMessage = "An error occurred"
        val rocketLaunches = emptyList<RocketLaunch>()
        repository.setError(errorMessage, rocketLaunches)
        repository.setErrorState(true)

        // Act
        viewModel.retrieveData(rocketLaunch.flightNumber)
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect()
        }

        // Assert
        assertTrue(viewModel.state.value is RocketLaunchUiState.Error)
        assertEquals(
            RocketLaunchUiState.Error(errorMessage),
            viewModel.state.value
        )
        collectJob.cancel()
    }

    @Test
    fun retrieveData_successResult_emitsSuccessState() = runTest {
        // Arrange
        repository.setLoadingState(true)
        repository.setRocketLaunches(rocketLaunches)
        repository.setSuccessState(true)

        // Act
        viewModel.retrieveData(rocketLaunch.flightNumber)
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect()
        }

        // Assert
        assertTrue(viewModel.state.value is RocketLaunchUiState.Success)
        assertEquals(
            RocketLaunchUiState.Success(rocketLaunch),
            viewModel.state.value
        )
        collectJob.cancel()
    }

    @Test
    fun retrieveData_successResult_emitsSuccessStateWithCorrectData() = runTest {
        // Arrange
        repository.setLoadingState(true)
        repository.setRocketLaunches(rocketLaunches)
        repository.setSuccessState(true)

        // Act
        viewModel.retrieveData(rocketLaunch.flightNumber)
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect()
        }

        // Assert
        assertTrue(viewModel.state.value is RocketLaunchUiState.Success)
        assertEquals(
            RocketLaunchUiState.Success(rocketLaunch),
            viewModel.state.value
        )
        assertEquals(
            rocketLaunch.flightNumber,
            (viewModel.state.value as RocketLaunchUiState.Success).rocketLaunch!!.flightNumber
        )
        collectJob.cancel()
    }

    @Test
    fun retrieveData_errorResult_emitsErrorStateWithCorrectMessage() = runTest {
        // Arrange
        val errorMessage = "An error occurred"
        repository.setError(errorMessage, emptyList())
        repository.setErrorState(true)

        // Act
        viewModel.retrieveData(rocketLaunch.flightNumber)
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect()
        }

        // Assert
        assertTrue(viewModel.state.value is RocketLaunchUiState.Error)
        assertEquals(
            RocketLaunchUiState.Error(errorMessage),
            viewModel.state.value
        )
        assertEquals(
            errorMessage,
            (viewModel.state.value as RocketLaunchUiState.Error).errorMessage
        )
        collectJob.cancel()
    }
}
