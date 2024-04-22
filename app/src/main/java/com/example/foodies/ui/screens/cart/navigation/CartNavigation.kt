package com.example.foodies.ui.screens.cart.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.foodies.base.navigation.FoodiesNavigationDestination
import com.example.foodies.ui.screens.cart.CartScreen
import com.example.foodies.MainViewModel
import com.example.foodies.base.navigation.Direction
import com.example.foodies.base.navigation.currentRout
import com.example.foodies.base.navigation.slideIntoContainer
import com.example.foodies.base.navigation.slideOutOfContainer
import com.example.foodies.base.navigation.targetRout
import com.example.foodies.ui.screens.catalog.navigation.CatalogNavigation
import com.example.foodies.ui.screens.splash.navigation.SplashScreenEndNavigation

object CartNavigation: FoodiesNavigationDestination {
    override val route: String = "cart_route"
    override val destination: String = "cart_destination"
}

fun NavGraphBuilder.cart(
    onClickBack: () -> Unit,
    navigateTo: (destination: FoodiesNavigationDestination, route: String?) -> Unit,
    navController: NavController,
) {
    composable(
        route = CartNavigation.route,
        exitTransition = {
            when (targetRout()) {
                CatalogNavigation.route -> slideOutOfContainer(Direction.Right)
                else -> null
            }
        },
        enterTransition = {
            when (currentRout()) {
                CatalogNavigation.route -> slideIntoContainer(Direction.Left)
                SplashScreenEndNavigation.route -> slideIntoContainer(Direction.Right)
                else -> null
            }
        }
    ) { backStackEntry ->
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry(CatalogNavigation.route)
        }

        val viewModel = hiltViewModel<MainViewModel>(parentEntry)

        CartScreen(
            onClickBack = onClickBack,
            viewModel = viewModel,
            onClickOrder = {
                navigateTo(
                    SplashScreenEndNavigation,
                    null,
                )
            }
        )
    }
}