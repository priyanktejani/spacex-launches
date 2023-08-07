package com.spacex.launches.core.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.spacex.launches.core.database.dao.RocketLaunchDao
import com.spacex.launches.core.database.model.RocketLaunchEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RocketLaunchDaoTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: SpaceXDatabase
    private lateinit var rocketLaunchDao: RocketLaunchDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            SpaceXDatabase::class.java
        ).allowMainThreadQueries().build()
        rocketLaunchDao = database.rocketLaunchDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun getRocketLaunches_shouldReturnCorrectListOf_RocketLaunchEntity() = runBlocking {
        // Arrange
        val launches = launches
        rocketLaunchDao.insertOrIgnoreRocketLaunches(launches)

        // Act
        val result = rocketLaunchDao.getRocketLaunches()

        // Assert
        assertEquals(launches, result)
    }

    @Test
    fun getRocketLaunch_shouldReturnTheCorrect_RocketLaunchEntity() = runBlocking {
        // Arrange
        val launches = launches
        rocketLaunchDao.insertOrIgnoreRocketLaunches(launches)

        // Act
        val result = rocketLaunchDao.getRocketLaunch(1)

        // Assert
        assertEquals(launches[0], result)
    }

    @Test
    fun getRocketLaunch_shouldReturnNullWhen_RocketLaunchEntity_IsNotFound() = runBlocking {
        // Arrange
        val launches = launches
        rocketLaunchDao.insertOrIgnoreRocketLaunches(launches)

        // Act
        val result = rocketLaunchDao.getRocketLaunch(3)

        // Assert
        assertEquals(null, result)
    }

    @Test
    fun deleteLaunches_shouldDeleteAll_RocketLaunchEntity() = runBlocking {
        // Arrange
        val launches = launches
        rocketLaunchDao.insertOrIgnoreRocketLaunches(launches)

        // Act
        rocketLaunchDao.deleteLaunches()
        val result = rocketLaunchDao.getRocketLaunches()

        // Assert
        assertEquals(emptyList<RocketLaunchEntity>(), result)
    }

    private val launches = listOf(
        RocketLaunchEntity(1, "Launch 1", "2023-07-01", "Details 1",
            success = true,
            upcoming = false
        ),
        RocketLaunchEntity(2, "Launch 2", "2023-07-02", "Details 2",
            success = false,
            upcoming = true
        )
    )
}
