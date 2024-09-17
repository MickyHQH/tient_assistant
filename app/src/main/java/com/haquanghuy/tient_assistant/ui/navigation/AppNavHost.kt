package com.haquanghuy.tient_assistant.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.haquanghuy.tient_assistant.ui.screen.chat.ChatScreen
import com.haquanghuy.tient_assistant.ui.screen.home.HomeScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = AppRouter.Home.route,
        modifier = modifier,
    ) {
        composable(
            route = AppRouter.Home.route,
        ) {
            HomeScreen(
                gotoChat = {
                    navController.navigate(AppRouter.Chat.route)
                }
            )
        }
        composable(
            route = AppRouter.Chat.route,
        ) {
            ChatScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
