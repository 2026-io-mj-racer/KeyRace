package com.example.keyraceapp.data.repositories

import android.content.Context
import com.example.keyraceapp.domain.repositories.WordRepository
import com.example.keyraceapp.util.Resource
import kotlinx.coroutines.flow.Flow


class WordRepositoryImpl(
    val context: Context
): WordRepository {
    override fun getWords(): Flow<Resource<List<String>>> {
        TODO("Not yet implemented")
    }

}

