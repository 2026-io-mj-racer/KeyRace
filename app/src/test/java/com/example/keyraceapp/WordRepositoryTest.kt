package com.example.keyraceapp

import android.content.Context
import android.content.res.AssetManager
import com.example.keyraceapp.data.repositories.WordRepositoryImpl
import com.example.keyraceapp.domain.repositories.WordRepository
import com.example.keyraceapp.util.Resource
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException
import kotlin.test.assertEquals

class WordRepositoryTest {

    val context = mockk<Context>()
    val assets = mockk<AssetManager>()
    private lateinit var wordRepository: WordRepository



    @Before
    fun setup() {
        every {context.assets} returns assets
        wordRepository = WordRepositoryImpl(context)
    }

    @Test
    fun `getWords returns loading and then success with data read from the assets`() = runTest {
        val fakeFileContent = """
        ABC
        DEF
        GHI
    """.trimIndent().byteInputStream()

        every { assets.open("words.txt") } returns fakeFileContent
        val expectedData = listOf("ABC", "DEF", "GHI")

        val emissions = wordRepository.getWords().toList()

        assertEquals(
            listOf(
                Resource.Loading<List<String>>(),
                Resource.Success(expectedData)
            ),
            emissions
        )
    }

    @Test
    fun `getWords returns Error with emptyList when AssetManager throws an exception`() = runTest {

        every {assets.open("words.txt")} throws IOException()

        val emission = wordRepository.getWords().toList()

        assertEquals(
            listOf(
                Resource.Loading<List<String>>(),
                Resource.Error(data = emptyList<String>(), message = "Unable to read the data from file")
            ),
            emission
        )
    }

}