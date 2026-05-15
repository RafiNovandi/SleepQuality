package com.muhammadrafinovandi0108.sleepquality.navigasi


const val KEY_ID_TIDUR = "idTidur"
sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
    data object Bedtime : Screen("bedtimeScreen")
    data object Trash : Screen("trashScreen")
    data object About: Screen("aboutScreen")
    data object FormBaru: Screen("detailScreen")
    data object FormEdit : Screen("detailScreen/{$KEY_ID_TIDUR}") {
        fun withId(id: Long) = "detailScreen/$id"
    }
}