package com.example.keyraceapp.di

import android.content.Context
import com.example.keyraceapp.data.repositories.WordRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object WordRepositoryModule {

    @Provides
    fun provideWordRepository(@ApplicationContext context: Context) =
        WordRepositoryImpl(context = context)

}
