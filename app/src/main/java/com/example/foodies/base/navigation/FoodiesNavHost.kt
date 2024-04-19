package com.example.foodies.base.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseInOutQuad
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.foodies.ui.screens.cart.navigation.cart
import com.example.foodies.ui.screens.catalog.navigation.catalog
import com.example.foodies.ui.screens.detail.navigation.detail
import com.example.foodies.ui.screens.search.navigation.search
import com.example.foodies.ui.screens.splash.navigation.SplashScreenStartNavigation
import com.example.foodies.ui.screens.splash.navigation.splash

@Composable
fun FoodiesNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    onNavigationToDestination: (destination: FoodiesNavigationDestination, route: String?) -> Unit,
    onClickBack: () -> Unit,
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = SplashScreenStartNavigation.route
    ) {
        splash(
            navigateTo = onNavigationToDestination
        )

        catalog(
            navigateTo = onNavigationToDestination,
            navController = navHostController
        )
        detail(
            navigateTo = onNavigationToDestination,
            navController = navHostController,
            onClickBack = onClickBack,
        )
        cart(
            onClickBack = onClickBack,
            navController = navHostController,
            navigateTo = onNavigationToDestination,
        )
        search(
            navigateTo = onNavigationToDestination,
            onClickBack = onClickBack,
            navController = navHostController,
        )
    }
}

const val DURATION_NAVIGATION_ANIMATION = 250

fun AnimatedContentTransitionScope<NavBackStackEntry>.currentRout() = initialState.destination.route

fun AnimatedContentTransitionScope<NavBackStackEntry>.targetRout() = targetState.destination.route

fun AnimatedContentTransitionScope<NavBackStackEntry>.slideIntoContainer(direction: Direction): EnterTransition {
    val slideDirection = when (direction) {
        Direction.Right -> AnimatedContentTransitionScope.SlideDirection.Right
        Direction.Left -> AnimatedContentTransitionScope.SlideDirection.Left
        Direction.Up -> AnimatedContentTransitionScope.SlideDirection.Up
        Direction.Down -> AnimatedContentTransitionScope.SlideDirection.Down
    }
    return slideIntoContainer(
        slideDirection,
        animationSpec = tween(
            durationMillis = DURATION_NAVIGATION_ANIMATION,
            easing = EaseInOutQuad
        )
    )
}

fun AnimatedContentTransitionScope<NavBackStackEntry>.slideOutOfContainer(direction: Direction): ExitTransition {
    val slideDirection = when (direction) {
        Direction.Right -> AnimatedContentTransitionScope.SlideDirection.Right
        Direction.Left -> AnimatedContentTransitionScope.SlideDirection.Left
        Direction.Up -> AnimatedContentTransitionScope.SlideDirection.Up
        Direction.Down -> AnimatedContentTransitionScope.SlideDirection.Down
    }
    return slideOutOfContainer(
        slideDirection,
        animationSpec = tween(
            durationMillis = DURATION_NAVIGATION_ANIMATION,
            easing = EaseInOutQuad
        )
    )
}

enum class Direction {
    Right, Left, Up, Down
}