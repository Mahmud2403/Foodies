package com.example.foodies.ui.screens.search.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.foodies.base.navigation.FoodiesNavigationDestination
import com.example.foodies.ui.screens.cart.navigation.CartNavigation
import com.example.foodies.CatalogViewModel
import com.example.foodies.base.navigation.Direction
import com.example.foodies.base.navigation.currentRout
import com.example.foodies.base.navigation.slideIntoContainer
import com.example.foodies.base.navigation.slideOutOfContainer
import com.example.foodies.base.navigation.targetRout
import com.example.foodies.ui.screens.catalog.navigation.CatalogNavigation
import com.example.foodies.ui.screens.detail.navigation.DetailProductNavigation
import com.example.foodies.ui.screens.search.SearchScreen


object SearchNavigation : FoodiesNavigationDestination {
    override val route = "search_route"
    override val destination = "search_destination"
}

fun NavGraphBuilder.search(
    navigateTo: (destination: FoodiesNavigationDestination, route: String?) -> Unit,
    navController: NavController,
    onClickBack: () -> Unit
) {
    composable(
        route = SearchNavigation.route,
        enterTransition = {
            when (currentRout()) {
                CatalogNavigation.route -> slideIntoContainer(Direction.Left)
                else -> null
            }
        },
        exitTransition = {
            when (targetRout()) {
                CatalogNavigation.route -> slideOutOfContainer(Direction.Right)
                else -> null
            }
        },
    ) { backStackEntry ->
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry(CatalogNavigation.route)
        }

        val viewModel = hiltViewModel<CatalogViewModel>(parentEntry)

        SearchScreen(
            onBackClick = onClickBack,
            viewModel = viewModel,
            onClickProduct = { id ->
                navigateTo(
                    DetailProductNavigation,
                    DetailProductNavigation.navigateWithArgument(id)
                )
            },
            onClickCart = {
                navigateTo(
                    CartNavigation,
                    null
                )
            }
        )
    }
}