package com.example.keyraceapp.di

import com.example.keyraceapp.data.local.KeyRaceDatabase
import com.example.keyraceapp.data.repositories.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
object UserRepositoryModule {

    @Provides
    fun provideUserRepository(database: KeyRaceDatabase) = UserRepositoryImpl(database)
}
