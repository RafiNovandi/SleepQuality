package com.muhammadrafinovandi0108.sleepquality.navigasi

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
    data object Detail: Screen("detailScreen")
    data object About: Screen("aboutScreen")
}