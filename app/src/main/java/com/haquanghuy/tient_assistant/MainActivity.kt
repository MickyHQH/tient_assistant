package com.haquanghuy.tient_assistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.haquanghuy.tient_assistant.ui.AppScreen
import com.haquanghuy.tient_assistant.ui.theme.TientAssistantTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TientAssistantTheme {
                AppScreen()
            }
        }
    }
}
