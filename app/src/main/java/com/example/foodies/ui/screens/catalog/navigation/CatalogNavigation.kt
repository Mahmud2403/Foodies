package com.example.foodies.ui.screens.catalog.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.foodies.base.navigation.FoodiesNavigationDestination
import com.example.foodies.ui.screens.cart.navigation.CartNavigation
import com.example.foodies.ui.screens.catalog.CatalogScreen
import com.example.foodies.MainViewModel
import com.example.foodies.ui.screens.detail.navigation.DetailProductNavigation
import com.example.foodies.ui.screens.search.navigation.SearchNavigation

object CatalogNavigation: FoodiesNavigationDestination {
    override val route: String = "catalog_route"
    override val destination: String = "catalog_destination"
}

fun NavGraphBuilder.catalog(
    navController: NavController,
    navigateTo: (destination: FoodiesNavigationDestination, route: String?) -> Unit,
) {
    composable (
        route = CatalogNavigation.route,
    ) { backStackEntry ->
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry(CatalogNavigation.route)
        }

        val viewModel = hiltViewModel<MainViewModel>(parentEntry)


        CatalogScreen(
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
            },
            onClickSearch = {
                navigateTo(
                    SearchNavigation,
                    null
                )
            },
        )
    }
}