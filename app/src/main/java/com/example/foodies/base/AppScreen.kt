package com.example.foodies.base

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.foodies.base.navigation.FoodiesNavHost

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppScreen(
    modifier: Modifier = Modifier,
    appState: FoodiesAppState,
) {
    Scaffold(
        modifier = modifier
            .navigationBarsPadding(),
        containerColor = Color.White,
    ) {
        FoodiesNavHost(
            navHostController = appState.navController,
            onNavigationToDestination = appState::navigate,
            onClickBack = appState::onBackClick,
        )
    }
}

