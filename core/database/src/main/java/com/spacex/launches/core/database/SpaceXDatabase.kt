package com.spacex.launches.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.spacex.launches.core.database.dao.RocketLaunchDao
import com.spacex.launches.core.database.model.RocketLaunchEntity


/**
 * Room database for setup for application.
 */
@Database(
    entities = [
        RocketLaunchEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class SpaceXDatabase : RoomDatabase() {
    /**
     * Dao for operating on the Room database.
     */
    abstract fun rocketLaunchDao(): RocketLaunchDao

    companion object {
        const val DATABASE_NAME = "spacex_db"
    }
}