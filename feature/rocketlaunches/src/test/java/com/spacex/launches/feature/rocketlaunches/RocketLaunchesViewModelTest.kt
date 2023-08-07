package com.spacex.launches.feature.rocketlaunches

import com.spacex.launches.core.data.repository.FakeSpaceXRepository
import com.spacex.launches.core.model.data.RocketLaunch
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class RocketLaunchesViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var repository: FakeSpaceXRepository
    private lateinit var viewModel: RocketLaunchesViewModel

    private val rocketLaunches = listOf(
        RocketLaunch(1, "Launch 1", "2023-07-01", "Details 1", success = true, upcoming = false),
        RocketLaunch(2, "Launch 2", "2023-07-02", "Details 2", success = false, upcoming = true),
        RocketLaunch(3, "Launch 3", "2023-07-03", "Details 3", success = false, upcoming = false)
    )

    @Before
    fun setUp() {
        repository = FakeSpaceXRepository()
        viewModel = RocketLaunchesViewModel(repository = repository)
    }

    @Test
    fun loadingState_emitsLoading() = runTest {
        // Act
        viewModel.retrieveData()
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect()
        }

        // Assert
        assertTrue(viewModel.state.value is RocketLaunchesUiState.Loading)
        assertEquals(RocketLaunchesUiState.Loading, viewModel.state.value)
        collectJob.cancel()
    }

    @Test
    fun emptyListFromRepository_emitsEmptyState() = runTest {
        // Arrange
        repository.setLoadingState(true)
        repository.setSuccessState(true)

        // Act
        viewModel.retrieveData()
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect()
        }

        // Assert
        assertTrue(viewModel.state.value is RocketLaunchesUiState.Empty)
        assertEquals(RocketLaunchesUiState.Empty, viewModel.state.value)
        collectJob.cancel()
    }

    @Test
    fun nonEmptyListFromRepository_emitsSuccessStateWithList() = runTest {
        // Arrange
        repository.setLoadingState(true)
        repository.setRocketLaunches(rocketLaunches)
        repository.setSuccessState(true)

        // Act
        viewModel.retrieveData()
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect()
        }

        // Assert
        assertTrue(viewModel.state.value is RocketLaunchesUiState.Success)
        assertEquals(RocketLaunchesUiState.Success(rocketLaunches), viewModel.state.value)
        collectJob.cancel()
    }

    @Test
    fun nonEmptyListFromRepository_emitsSuccessStateWithListSize() = runTest {
        // Arrange
        repository.setLoadingState(true)
        repository.setRocketLaunches(rocketLaunches)
        repository.setSuccessState(true)

        // Act
        viewModel.retrieveData()
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect()
        }

        // Assert
        assertTrue(viewModel.state.value is RocketLaunchesUiState.Success)
        assertEquals(RocketLaunchesUiState.Success(rocketLaunches), viewModel.state.value)
        assertEquals(
            rocketLaunches.size,
            (viewModel.state.value as RocketLaunchesUiState.Success).rocketLaunches!!.size
        )
        collectJob.cancel()
    }

    @Test
    fun nonEmptyListFromRepository_emitsSuccessStateWithListFirstObjectName() = runTest {
        // Arrange
        repository.setLoadingState(true)
        repository.setRocketLaunches(rocketLaunches)
        repository.setSuccessState(true)

        // Act
        viewModel.retrieveData()
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect()
        }

        // Assert
        assertTrue(viewModel.state.value is RocketLaunchesUiState.Success)
        assertEquals(RocketLaunchesUiState.Success(rocketLaunches), viewModel.state.value)
        assertEquals(
            rocketLaunches[0].name,
            (viewModel.state.value as RocketLaunchesUiState.Success).rocketLaunches!![0].name
        )
        collectJob.cancel()
    }

    @Test
    fun errorFromRepository_emitsErrorStateWithEmptyList() = runTest {
        // Arrange
        repository.setLoadingState(true)
        val errorMessage = "An error occurred"
        val rocketLaunches = emptyList<RocketLaunch>()
        repository.setError(errorMessage, rocketLaunches)
        repository.setErrorState(true)

        // Act
        viewModel.retrieveData()
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect()
        }

        // Assert
        assertTrue(viewModel.state.value is RocketLaunchesUiState.Error)
        assertEquals(
            RocketLaunchesUiState.Error(rocketLaunches = rocketLaunches, errorMessage),
            viewModel.state.value
        )
        assertEquals(
            errorMessage,
            (viewModel.state.value as RocketLaunchesUiState.Error).errorMessage
        )
        assertEquals(
            rocketLaunches,
            (viewModel.state.value as RocketLaunchesUiState.Error).rocketLaunches
        )

        collectJob.cancel()
    }

    @Test
    fun errorFromRepository_emitsErrorStateWithNonEmptyList() = runTest {
        // Arrange
        repository.setLoadingState(true)
        val errorMessage = "An error occurred"
        repository.setError(errorMessage, rocketLaunches)
        repository.setErrorState(true)

        // Act
        viewModel.retrieveData()
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect()
        }

        // Assert
        assertTrue(viewModel.state.value is RocketLaunchesUiState.Error)
        assertEquals(
            RocketLaunchesUiState.Error(rocketLaunches = rocketLaunches, errorMessage),
            viewModel.state.value
        )
        assertEquals(
            errorMessage,
            (viewModel.state.value as RocketLaunchesUiState.Error).errorMessage
        )
        assertEquals(
            rocketLaunches,
            (viewModel.state.value as RocketLaunchesUiState.Error).rocketLaunches
        )

        collectJob.cancel()
    }

    @Test
    fun errorFromRepository_emitsErrorStateWithListFirstObjectName() = runTest {
        // Arrange
        repository.setLoadingState(true)
        val errorMessage = "An error occurred"
        repository.setError(errorMessage, rocketLaunches)
        repository.setErrorState(true)

        // Act
        viewModel.retrieveData()
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect()
        }

        // Assert
        assertTrue(viewModel.state.value is RocketLaunchesUiState.Error)
        assertEquals(
            RocketLaunchesUiState.Error(rocketLaunches = rocketLaunches, errorMessage),
            viewModel.state.value
        )
        assertEquals(
            errorMessage,
            (viewModel.state.value as RocketLaunchesUiState.Error).errorMessage
        )
        assertEquals(
            rocketLaunches[0].name,
            (viewModel.state.value as RocketLaunchesUiState.Error).rocketLaunches!![0].name
        )

        collectJob.cancel()
    }

    @Test
    fun errorFromRepository_emitsErrorStateWithListSize() = runTest {
        // Arrange
        repository.setLoadingState(true)
        val errorMessage = "An error occurred"
        repository.setError(errorMessage, rocketLaunches)
        repository.setErrorState(true)

        // Act
        viewModel.retrieveData()
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.state.collect()
        }

        // Assert
        assertTrue(viewModel.state.value is RocketLaunchesUiState.Error)
        assertEquals(
            RocketLaunchesUiState.Error(rocketLaunches = rocketLaunches, errorMessage),
            viewModel.state.value
        )
        assertEquals(
            errorMessage,
            (viewModel.state.value as RocketLaunchesUiState.Error).errorMessage
        )
        assertEquals(
            rocketLaunches.size,
            (viewModel.state.value as RocketLaunchesUiState.Error).rocketLaunches!!.size
        )

        collectJob.cancel()
    }
}
