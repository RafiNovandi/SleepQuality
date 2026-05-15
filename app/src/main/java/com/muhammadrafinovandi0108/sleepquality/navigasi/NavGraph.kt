package com.muhammadrafinovandi0108.sleepquality.navigasi

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.muhammadrafinovandi0108.sleepquality.screen.AboutScreen
import com.muhammadrafinovandi0108.sleepquality.screen.BedtimeScreen
import com.muhammadrafinovandi0108.sleepquality.screen.DetailScreen
import com.muhammadrafinovandi0108.sleepquality.screen.MainScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable( route = Screen.Home.route ) {
            MainScreen(navController)
        }
        composable(route = Screen.About.route) {
            AboutScreen(navController)
        }
        composable(route = Screen.Bedtime.route) {
            BedtimeScreen(navController)
        }
        composable(route = Screen.FormBaru.route) {
            DetailScreen(navController)
        }
        composable(
            route = Screen.FormEdit.route,
            arguments = listOf(
                navArgument(KEY_ID_TIDUR) { type = NavType.LongType }
            )
        ) { backStack ->
            val id = backStack.arguments?.getLong(KEY_ID_TIDUR)
            DetailScreen(navController, id)
        }
    }
}