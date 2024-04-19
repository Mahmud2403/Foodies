package com.example.foodies.ui.screens.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.ModifierLocalModifierNode
import androidx.compose.ui.unit.dp

@Composable
fun ProductCharacteristics(
    modifier: Modifier = Modifier,
    name: String,
    meaning: String,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = 13.dp,
                horizontal = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = name,
            color = Color.Black.copy(0.5f)
        )
        Text(
            text = meaning,
            color = Color.Black,
        )
    }
}