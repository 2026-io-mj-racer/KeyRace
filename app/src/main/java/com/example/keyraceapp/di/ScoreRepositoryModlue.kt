package com.example.keyraceapp.di

import androidx.room.Database
import com.example.keyraceapp.data.local.KeyRaceDatabase
import com.example.keyraceapp.data.repositories.ScoreRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
object ScoreRepositoryModule {

    @Provides
    fun provideScoreRepository(database: KeyRaceDatabase) = ScoreRepositoryImpl(database)
}
