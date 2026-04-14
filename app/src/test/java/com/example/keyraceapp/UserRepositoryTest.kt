package com.example.keyraceapp

import com.example.keyraceapp.data.dao.UserDao
import com.example.keyraceapp.data.local.KeyRaceDatabase
import com.example.keyraceapp.data.local.entities.UserEntity
import com.example.keyraceapp.data.repositories.UserRepositoryImpl
import com.example.keyraceapp.domain.models.User
import com.example.keyraceapp.domain.repositories.UserRepository
import com.example.keyraceapp.util.Resource
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class UserRepositoryTest {
    val db = mockk<KeyRaceDatabase>()
    val userDao = mockk<UserDao>()
    private lateinit var userRepository: UserRepository


    private val exampleUser =
        User(name = "John")
    private val exampleUserEntity =
        UserEntity(name = "John")

    @Before
    fun setup() {
        every {db.userDao} returns userDao

        userRepository = UserRepositoryImpl(db)
    }

    @Test
    fun `getUser() - returns Success with User when database has the user`() = runTest {
        coEvery {userDao.getUser()}  returns exampleUserEntity
        val expected = Resource.Success(exampleUser)

        val actual = userRepository.getUser()

        assertEquals(expected, actual)
    }

    @Test
    fun `getUser() - returns Error with null as user when exception was thrown`() = runTest {
        coEvery {userDao.getUser()} throws RuntimeException("Unable to fetch user")
        val expected = Resource.Error<User>(message = "Unable to fetch user")

        val actual = userRepository.getUser()

        assertEquals(expected, actual)
    }

    @Test
    fun `saveUser() - returns Success with Nothing when user saved correctly`() = runTest {
        coEvery {userDao.insert(exampleUserEntity)} just Runs

        val actual = userRepository.saveUser(exampleUser)

        assertEquals(Resource.Success(Unit), actual)
    }
    @Test
    fun `saveUser() - returns Error when inserting a user throws an exception`() = runTest {
        coEvery { userDao.insert(exampleUserEntity) } throws RuntimeException("Unable to insert user")

        val actual = userRepository.saveUser(exampleUser)

        assertEquals(Resource.Error(message = "Unable to insert user"), actual)
    }

    @Test
    fun `resetData() - returns Success when deletion is done`() = runTest {
        coEvery { userDao.deleteUser() } just Runs

        val actual = userRepository.resetData(exampleUser)

        assertEquals(Resource.Success(Unit), actual)
    }

    @Test
    fun `resetData() - returns Error when deletion is throws an exception`() = runTest {
        coEvery { userDao.deleteUser() } throws RuntimeException("Unable to delete user")

        val actual = userRepository.resetData(exampleUser)

        assertEquals(Resource.Error("Unable to delete user"), actual)
    }
}