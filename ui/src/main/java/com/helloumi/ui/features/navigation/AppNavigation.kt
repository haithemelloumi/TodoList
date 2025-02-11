package com.helloumi.ui.feature.navigation

sealed class AppNavigation(val destination: String) {
    data object Home: AppNavigation("home")
}
