package com.haquanghuy.tient_assistant.ui.navigation

sealed class AppRouter(val route: String) {
    data object Home : AppRouter("home")
    data object Chat : AppRouter("chat")
}

