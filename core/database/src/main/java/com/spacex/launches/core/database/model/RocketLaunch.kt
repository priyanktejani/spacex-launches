package com.spacex.launches.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Model object to store data about a rocket launch entity.
 */
@Entity(
    tableName = "rocket_launches"
)
data class RocketLaunchEntity(
    @PrimaryKey
    @ColumnInfo(name = "flight_number")
    val flightNumber: Int,
    val name: String,
    @ColumnInfo(name = "date_utc")
    val dateUTC: String,
    val details: String?,
    val success: Boolean?,
    val upcoming: Boolean?

)
