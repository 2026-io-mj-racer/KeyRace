package com.example.keyraceapp

import android.R.attr.data
import android.os.Bundle
import android.util.Log
import android.util.Log.i
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.room.util.TableInfo
import com.example.keyraceapp.data.repositories.WordRepositoryImpl
import com.example.keyraceapp.domain.repositories.WordRepository
import com.example.keyraceapp.ui.theme.KeyRaceAppTheme
import com.example.keyraceapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.emptyList

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repo = WordRepositoryImpl(applicationContext)
        enableEdgeToEdge()
        setContent {
            KeyRaceAppTheme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->


//                    WTF?!!
//                    val repo = WordRepositoryImpl(applicationContext)
//                    val state by repo.getWords().collectAsState(null)
//                    val wordList = state?.data ?: emptyList()

                    Greeting(
                        wordsFlow = repo.getWords(),
                        modifier = Modifier.padding(innerPadding)
                    )
              }
            }
        }
    }
}



//Only for test to see if app runs
@Composable
fun Greeting(wordsFlow: Flow<Resource<List<String>>>, modifier: Modifier = Modifier) {

    val state by remember {wordsFlow}.collectAsState(null)
    val wordList = state?.data ?: emptyList()

    Log.d("MAIN ACTIVITY", wordList.toString())
    Column {
        for (word in wordList) {
            Text(text = word)
        }
    }
}



