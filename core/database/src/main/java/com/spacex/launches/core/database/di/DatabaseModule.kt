package com.spacex.launches.core.database.di

import android.content.Context
import androidx.room.Room
import com.spacex.launches.core.database.SpaceXDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesSpaceXDatabase(
        @ApplicationContext context: Context,
    ): SpaceXDatabase = Room.databaseBuilder(
        context,
        SpaceXDatabase::class.java,
        SpaceXDatabase.DATABASE_NAME,
    ).build()
}