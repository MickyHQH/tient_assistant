package com.haquanghuy.tient_assistant.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.haquanghuy.tient_assistant.ui.navigation.AppNavHost

@Composable
fun AppScreen() {
    val navController = rememberNavController()
    AppNavHost(
        navController = navController,
    )
}