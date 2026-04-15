package com.example.keyraceapp.data.repositories

import android.content.Context
import android.media.MediaParser
import android.util.Log.e
import android.util.Log.i
import com.example.keyraceapp.domain.repositories.WordRepository
import com.example.keyraceapp.util.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.Buffer
import javax.inject.Inject


class WordRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
): WordRepository {
    private val WORDS_FILE = "words.txt"

    override fun getWords(): Flow<Resource<List<String>>> {
        return flow {
            try {
                emit(Resource.Loading())

                val input = context.assets.open(WORDS_FILE)
                val reader = BufferedReader(InputStreamReader(input))
                val wordsList = reader.readLines()

                emit(Resource.Success(wordsList))

            } catch(e: Exception) {
                emit(Resource.Error("Unable to read the data from file", emptyList<String>()))
            }
        }.flowOn(Dispatchers.IO)

    }
}

