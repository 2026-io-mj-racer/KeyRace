package com.example.keyraceapp.di

import android.R.attr.name
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.keyraceapp.data.local.KeyRaceDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun providesKeyRaceDb(context: Context): KeyRaceDatabase {
        return Room.databaseBuilder<KeyRaceDatabase>(
            context = context,
            name =  "KeyRace.db",
        ).build()
    }
}