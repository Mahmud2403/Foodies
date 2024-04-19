package com.example.foodies.ui.screens.catalog.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodies.R
import com.example.foodies.utils.clickableWithRipple

@Preview
@Composable
private fun CatalogTopBarPreview() {
    CatalogTopBar(
        onClickFilter = {},
        onClickSearch = {},
        filterCount = 1,
    )
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun CatalogTopBar(
    modifier: Modifier = Modifier,
    onClickFilter: () -> Unit,
    onClickSearch: () -> Unit,
    filterCount: Int,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(
                start = 8.dp,
                top = 16.dp,
                end = 8.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .clickableWithRipple(
                    onClick = onClickFilter
                )
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp),
                painter = painterResource(id = R.drawable.ic_filter),
                contentDescription = null,
                tint = Color.Black,
            )
            if (filterCount > 0) {
                FilterCount(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 10.dp, y = (-10).dp),
                    filterCount = filterCount
                )
            }
        }
        Image(
            modifier = Modifier
                .size(
                    width = 144.dp,
                    height = 44.dp
                ),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
        )
        Icon(
            modifier = Modifier
                .clickableWithRipple(
                    onClick = onClickSearch
                ),
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = null,
            tint = Color.Black,
        )
    }
}

@Composable
private fun FilterCount(
    modifier: Modifier = Modifier,
    filterCount: Int
) {
    Badge(
        modifier = modifier
            .border(1.dp, color = Color.White, shape = CircleShape),
        containerColor = Color(0xFFF15412),
        contentColor = Color.White,
    ) {
        Text(
            text = filterCount.toString(),
            fontSize = 10.sp,
            fontWeight = FontWeight(600),
            color = Color.White
        )
    }
}