package com.example.keyraceapp.di

import com.example.keyraceapp.data.local.KeyRaceDatabase
import com.example.keyraceapp.data.repositories.ScoreRepositoryImpl
import com.example.keyraceapp.domain.repositories.ScoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
object ScoreRepositoryModule {

    @Provides
    fun provideScoreRepository(database: KeyRaceDatabase): ScoreRepository = ScoreRepositoryImpl(database)
}
