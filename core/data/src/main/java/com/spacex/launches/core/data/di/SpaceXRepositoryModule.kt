package com.spacex.launches.core.data.di

import com.spacex.launches.core.data.repository.SpaceXRepository
import com.spacex.launches.core.data.repository.SpaceXRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SpaceXRepositoryModule {
    @Binds
    @Singleton
    fun bindsSpaceXRepository(
        userDataRepository: SpaceXRepositoryImpl,
    ): SpaceXRepository
}