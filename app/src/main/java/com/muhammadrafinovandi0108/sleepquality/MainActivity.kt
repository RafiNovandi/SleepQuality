package com.muhammadrafinovandi0108.sleepquality

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.muhammadrafinovandi0108.sleepquality.screen.MainScreen
import com.muhammadrafinovandi0108.sleepquality.ui.theme.SleepQualityTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SleepQualityTheme {
                MainScreen()
            }
        }
    }
}
