package com.example.foodies.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.foodies.base.navigation.FoodiesNavigationDestination
import com.google.accompanist.systemuicontroller.SystemUiController

@Composable
fun rememberFoodiesAppState(
    navController: NavHostController = rememberNavController(),
): FoodiesAppState {
    return remember(navController) {
        FoodiesAppState(navController)
    }
}

@Stable
class FoodiesAppState(
    val navController: NavHostController,
) {

    fun navigate(destination: FoodiesNavigationDestination, route: String? = null) {
        navController.navigate(route ?: destination.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
                inclusive = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun onBackClick() {
        navController.popBackStack()
    }
}
