package com.example.keyraceapp.domain.repositories

import com.example.keyraceapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface WordRepository {
    fun getWords(): Flow<Resource<List<String>>>
}
