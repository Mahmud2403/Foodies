package com.example.foodies.ui.screens.catalog.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.foodies.Filter

@Composable
fun FilterCheckBox(
    modifier: Modifier = Modifier,
    filter: Filter,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = filter.value,
            color = Color.Black,
        )
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                uncheckedColor = Color(0f, 0f, 0f, 0.12f),
                checkedColor = Color(0xFFF15412),
                checkmarkColor = Color.White,
            )
        )
    }
}