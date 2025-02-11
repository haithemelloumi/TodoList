package com.helloumi.ui.feature.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.helloumi.todolist.ui.theme.Purple40
import com.helloumi.ui.features.home.HomeScreen

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupNavGraph() {
    val navController: NavHostController = rememberNavController()

    Scaffold(
        containerColor = Color.White,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Home")
                },
                // Customize Colors here
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Purple40,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
            )
        },
        content = { innerPadding ->

            NavHost(
                navController = navController,
                startDestination = AppNavigation.Home.destination
            ) {
                composable(AppNavigation.Home.destination) {
                    HomeScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }

            }

        },
    )
}

