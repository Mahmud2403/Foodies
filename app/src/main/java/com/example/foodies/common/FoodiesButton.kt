package com.example.foodies.common

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun FoodiesButton(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    enable: Boolean = true,
    shape: Shape = MaterialTheme.shapes.medium,
    innerPaddingValues: PaddingValues = PaddingValues(10.dp),
    onClick: () -> Unit,
    containerColor: Color = Color(0xFFF15412),
    content: @Composable RowScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .graphicsLayer(
                shape = shape,
                clip = true,
                spotShadowColor = Color.Gray,
            )
            .background(
                animateColorAsState(
                    targetValue = if (isLoading) Color(0xFFB1B9BB) else containerColor,
                    animationSpec = tween(300),
                    label = "",
                ).value
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = Color.LightGray),
                enabled = enable && !isLoading,
                onClick = onClick,
            )
            .padding(innerPaddingValues),
        contentAlignment = Alignment.Center,
        content = {
            Row(
                modifier = Modifier
                    .graphicsLayer {
                        alpha = if (isLoading) 0f else 1f
                    }
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                content = {
                    content(this)
                    AnimatedContent(
                        targetState = isLoading,
                        label = "",
                    ) {
                        if (it) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(20.dp),
                                strokeWidth = 1.dp,
                                color = Color.White
                            )
                        }
                    }
                }
            )
        }
    )
}