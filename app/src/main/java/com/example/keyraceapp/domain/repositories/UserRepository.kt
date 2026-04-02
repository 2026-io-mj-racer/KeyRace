package com.example.keyraceapp.domain.repositories

import com.example.keyraceapp.domain.models.User

interface UserRepository {
    fun getUser(): User
    fun saveUser(user: User)
    fun resetData(user: User)

}
