package com.example.foodies.base.navigation

import androidx.compose.runtime.Composable

interface FoodiesNavigationDestination {
    val route: String
    val destination: String
}