package com.example.foodies.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun EmptyList(
    modifier: Modifier = Modifier,
    value: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier,
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0f, 0f, 0f, 0.6f),
            textAlign = TextAlign.Center,
        )
    }
}