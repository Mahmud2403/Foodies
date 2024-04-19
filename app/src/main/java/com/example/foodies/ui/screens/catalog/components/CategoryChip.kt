package com.example.foodies.ui.screens.catalog.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun CategoryChip(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    name: String,
    onClickCategory: () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) Color(0xFFF15412) else Color.White)
            .clickable(
                onClick = onClickCategory
            )
    ) {
        Text(
            modifier = modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),
            text = name,
            color = if (isSelected) Color.White else Color.Black
        )
    }
}