package com.example.foodies.ui.screens.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.foodies.base.navigation.Direction
import com.example.foodies.base.navigation.FoodiesNavigationDestination
import com.example.foodies.base.navigation.slideOutOfContainer
import com.example.foodies.base.navigation.targetRout
import com.example.foodies.ui.screens.catalog.navigation.CatalogNavigation
import com.example.foodies.ui.screens.splash.SplashScreenEnd
import com.example.foodies.ui.screens.splash.SplashScreenStart

object SplashScreenStartNavigation : FoodiesNavigationDestination {
    override val route: String = "splash_start_route"
    override val destination: String = "splash_start_destination"
}

object SplashScreenEndNavigation : FoodiesNavigationDestination {
    override val route: String = "splash_end_route"
    override val destination: String = "splash_end_destination"
}

fun NavGraphBuilder.splash(
    navigateTo: (destination: FoodiesNavigationDestination, route: String?) -> Unit,
) {
    composable(
        route = SplashScreenStartNavigation.route,
        exitTransition = {
            when (targetRout()) {
                SplashScreenStartNavigation.route -> slideOutOfContainer(Direction.Right)
                else -> null
            }
        }
    ) {
        SplashScreenStart(
            onSplashScreenFinish = {
                navigateTo(
                    CatalogNavigation,
                    null
                )
            }
        )
    }

    composable(
        route = SplashScreenEndNavigation.route,
        exitTransition = {
            when (targetRout()) {
                SplashScreenStartNavigation.route -> slideOutOfContainer(Direction.Right)
                else -> null
            }
        }
    ) {
        SplashScreenEnd()
    }
}