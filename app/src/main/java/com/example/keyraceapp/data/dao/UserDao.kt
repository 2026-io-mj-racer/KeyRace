package com.example.keyraceapp.data.dao

import com.example.keyraceapp.data.local.entities.UserEntity
import com.example.keyraceapp.domain.models.User

interface UserDao {
    suspend fun getTotalWords(): Long
    suspend fun getTotalGames(): Long

    suspend fun deleteUser()

    suspend fun getUser(): UserEntity

    suspend fun insert(user: User)

}

