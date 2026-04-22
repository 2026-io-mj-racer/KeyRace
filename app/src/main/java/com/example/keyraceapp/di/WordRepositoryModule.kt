package com.example.keyraceapp.di

import android.content.Context
import com.example.keyraceapp.data.repositories.WordRepositoryImpl
import com.example.keyraceapp.domain.repositories.WordRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object WordRepositoryModule {

    @Provides
    fun provideWordRepository(@ApplicationContext context: Context): WordRepository  =
        WordRepositoryImpl(context = context)

}
