package com.spacex.launches.core.network


import com.spacex.launches.core.network.model.NetworkRocketLaunch
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class SpaceXNetworkApiTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: SpaceXNetworkApi

    @Before
    fun setup() {
        // Set up the MockWebServer and Retrofit before each test.

        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
            ))
            .build()

        api = retrofit.create()
    }

    @After
    fun teardown() {
        // Shut down the MockWebServer after each test.

        mockWebServer.shutdown()
    }

    @Test
    fun getRocketLaunches_shouldReturnTheCorrectListOfNetworkRocketLaunch() = runBlocking {
        // Arrange
        // Create a list of expected NetworkRocketLaunch objects for comparison.
        val expectedResponse = listOf(
            NetworkRocketLaunch(1, "Launch 1", "2023-07-01", "Details 1",
                success = true,
                upcoming = false
            ),
            NetworkRocketLaunch(2, "Launch 2", "2023-07-02", "Details 2",
                success = false,
                upcoming = true
            )
        )

        // Define the response body in JSON format that the MockWebServer will return.
        val responseBody = """
            [
                {
                    "flight_number": 1,
                    "name": "Launch 1",
                    "date_utc": "2023-07-01",
                    "details": "Details 1",
                    "success": true,
                    "upcoming": false
                },
                {
                    "flight_number": 2,
                    "name": "Launch 2",
                    "date_utc": "2023-07-02",
                    "details": "Details 2",
                    "success": false,
                    "upcoming": true
                }
            ]
        """.trimIndent()

        // Enqueue the mock response with the JSON body.
        mockWebServer.enqueue(MockResponse().setBody(responseBody))

        // Act
        // Make the API call to get the rocket launches.
        val result = api.getRocketLaunches()

        // Assert
        // Compare the actual result with the expected response to check if the API call was successful.
        assertEquals(expectedResponse, result)
    }
}

