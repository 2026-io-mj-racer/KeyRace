package com.example.keyraceapp.di

import android.content.Context
import androidx.room.Room
import com.example.keyraceapp.data.local.KeyRaceDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun providesKeyRaceDb(@ApplicationContext context: Context): KeyRaceDatabase {
        return Room.databaseBuilder<KeyRaceDatabase>(
            context = context,
            name =  "KeyRace.db",
        ).build()
    }
}