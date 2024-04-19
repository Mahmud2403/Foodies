package com.example.foodies.ui.screens.splash

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.foodies.R

@Composable
fun SplashScreenStart(
    modifier: Modifier = Modifier,
    onSplashScreenFinish: () -> Unit,

) {
    val splashScreenLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.splash_screen
        )
    )

    val splashScreenProgress by animateLottieCompositionAsState(
        splashScreenLottieComposition,
        iterations = 1,
        isPlaying = true,
        restartOnPlay = false,
    )

    if (splashScreenProgress >= 1f) {
        LaunchedEffect(Unit) {
            onSplashScreenFinish()
        }
    }


    LottieAnimation(
        modifier = modifier
            .background(Color(0xFFF15412)),
        composition = splashScreenLottieComposition,
        progress = splashScreenProgress,
    )
}