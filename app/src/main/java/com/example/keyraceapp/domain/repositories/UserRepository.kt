package com.example.keyraceapp.domain.repositories

import com.example.keyraceapp.domain.models.User
import com.example.keyraceapp.util.Resource

interface UserRepository {
    suspend fun getUser(): Resource<User>
    suspend fun saveUser(user: User): Resource<Unit>
    suspend fun resetData(user: User): Resource<Unit>
    suspend fun changeUsername(name: String, userId: String): Resource<Unit>

}
