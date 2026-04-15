package com.example.keyraceapp.data.repositories

import com.example.keyraceapp.data.local.KeyRaceDatabase
import com.example.keyraceapp.data.mappers.toUser
import com.example.keyraceapp.data.mappers.toUserEntity
import com.example.keyraceapp.domain.models.User
import com.example.keyraceapp.domain.repositories.UserRepository
import com.example.keyraceapp.util.Resource
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val db: KeyRaceDatabase,
): UserRepository {
    private val userDao = db.userDao;

    override suspend fun getUser(): Resource<User> {
        try {
            val userEntity = userDao.getUser()
            return Resource.Success(userEntity.toUser())
        } catch (e: Exception) {
            return Resource.Error(message = "Unable to fetch user")
        }

    }

    override suspend fun saveUser(user: User): Resource<Unit> {
        try {
            userDao.insert(user.toUserEntity())
            return Resource.Success(Unit)
        } catch(e: Exception) {
            return Resource.Error(message = "Unable to insert user")

        }
    }

    override suspend fun resetData(user: User): Resource<Unit> {
        try {
            userDao.deleteUser()
            userDao.deleteArcadeScores()
            userDao.deleteTrainingScores()

            return Resource.Success(Unit)
        } catch(e: Exception) {
            return Resource.Error(message =  "Unable to delete user")
        }
    }

}
