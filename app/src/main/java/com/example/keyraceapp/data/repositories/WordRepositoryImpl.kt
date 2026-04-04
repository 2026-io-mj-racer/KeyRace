package com.example.keyraceapp.data.repositories

import com.example.keyraceapp.data.local.KeyRaceDatabase
import com.example.keyraceapp.domain.repositories.WordRepository
import com.example.keyraceapp.util.Resource
import kotlinx.coroutines.flow.Flow


class WordRepositoryImpl(
    private val db: KeyRaceDatabase
): WordRepository {
    override fun getWords(): Flow<Resource<List<String>>> {
        TODO("Not yet implemented")
    }

}

