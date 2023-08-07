package com.spacex.launches.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.spacex.launches.core.database.model.RocketLaunchEntity

/**
 * Dao for operating on the Room database.
 */
@Dao
interface RocketLaunchDao {
    /**
     * Retrieve stored [RocketLaunchEntity]s.
     */
    @Query("SELECT * FROM rocket_launches")
    suspend fun getRocketLaunches(): List<RocketLaunchEntity>

    /**
     * Retrieve stored [RocketLaunchEntity].
     */
    @Query("SELECT * FROM rocket_launches WHERE flight_number == (:flightNumber)")
    suspend fun getRocketLaunch(flightNumber: Int): RocketLaunchEntity?

    /**
     * Store new [RocketLaunchEntity] List.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrIgnoreRocketLaunches(launches: List<RocketLaunchEntity>)

    /**
     * Delete stored RocketLaunch's.
     */
    @Query("DELETE FROM rocket_launches")
    suspend fun deleteLaunches()
}
