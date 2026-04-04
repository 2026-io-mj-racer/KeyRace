package com.example.keyraceapp.data.repositories

import com.example.keyraceapp.data.local.KeyRaceDatabase
import com.example.keyraceapp.domain.models.User
import com.example.keyraceapp.domain.repositories.UserRepository
import com.example.keyraceapp.util.Resource

class UserRepositoryImpl(
    private val db: KeyRaceDatabase,
): UserRepository {
    override suspend fun getUser(): Resource<User> {
        TODO("Not yet implemented")
    }

    override suspend fun saveUser(user: User): Resource<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun resetData(user: User): Resource<Unit> {
        TODO("Not yet implemented")
    }

}
