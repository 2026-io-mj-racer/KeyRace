package com.example.keyraceapp.di

import com.example.keyraceapp.util.AndroidTimeProvider
import com.example.keyraceapp.util.TimeProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object TimeProviderModule {

    @Provides
    fun provideTimeProvider(): TimeProvider = AndroidTimeProvider()
}