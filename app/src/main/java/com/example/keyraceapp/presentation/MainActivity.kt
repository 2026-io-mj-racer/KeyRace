package com.example.keyraceapp.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.keyraceapp.navigation.KeyRaceHost
import com.example.keyraceapp.presentation.Game.GameViewModel
import com.example.keyraceapp.presentation.Splash.SplashScreen
import com.example.keyraceapp.ui.theme.KeyRaceAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KeyRaceAppTheme {
                var showSplash by remember { mutableStateOf(true) }


                Crossfade(showSplash) { splash ->
                    if (splash) {
                        SplashScreen(
                            text = "KeyRace",
                            onFinished = { showSplash = false },
                            modifier = Modifier.Companion.fillMaxSize()
                        )
                    } else {
                        KeyRaceHost()
                    }
                }
            }
        }
    }
}