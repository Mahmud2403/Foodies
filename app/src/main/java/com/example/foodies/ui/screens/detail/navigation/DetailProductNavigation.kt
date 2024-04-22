package com.example.foodies.ui.screens.detail.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.foodies.base.navigation.FoodiesNavigationDestination
import com.example.foodies.domain.model.Product
import com.example.foodies.MainViewModel
import com.example.foodies.ui.screens.catalog.navigation.CatalogNavigation
import com.example.foodies.ui.screens.detail.DetailProductScreen

object DetailProductNavigation: FoodiesNavigationDestination {
    override val route: String = "detail_route/{id}"
    override val destination: String = "detail_destination"

    fun navigateWithArgument(id: Int) =
        "detail_route/$id"
}

fun NavGraphBuilder.detail(
    navController: NavController,
    navigateTo: (destination: FoodiesNavigationDestination, route: String?) -> Unit,
    onClickBack: () -> Unit,
) {


    composable(
        route = DetailProductNavigation.route,
        arguments = listOf(
            navArgument("id") { type = NavType.IntType}
        )
    ) { backStackEntry ->
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry(CatalogNavigation.route)
        }

        val viewModel = hiltViewModel<MainViewModel>(parentEntry)

        val id = requireNotNull(backStackEntry.arguments?.getInt("id"))

        val product: Product = remember(id) {
            viewModel.getDetailProduct(id)
        }

        DetailProductScreen(
            onClickBack = onClickBack,
            product = product,
            viewModel = viewModel,
        )
    }
}