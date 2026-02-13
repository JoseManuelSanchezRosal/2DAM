package com.jose.dualclock.ui.navigation

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Settings : Screen("settings")
    object Reports : Screen("reports")
}
