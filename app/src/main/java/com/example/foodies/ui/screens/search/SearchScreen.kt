package com.example.foodies.ui.screens.search

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.foodies.R
import com.example.foodies.common.FoodiesButton
import com.example.foodies.common.FoodiesProductList
import com.example.foodies.CatalogViewModel
import com.example.foodies.common.EmptyList
import com.example.foodies.ui.screens.search.components.SearchTopBar
import com.example.foodies.utils.HorizontalSpacer


@SuppressLint("UnrememberedMutableInteractionSource", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onClickProduct: (Int) -> Unit,
    onClickCart: () -> Unit,
    viewModel: CatalogViewModel,
) {

    val uiState by viewModel.catalogUiState.collectAsStateWithLifecycle()
    val totalSum by rememberUpdatedState(newValue = uiState.totalSum)

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding(),
        topBar = {
            SearchTopBar(
                searchText = uiState.searchText,
                onValueChange = viewModel::changeSearchText,
                onClickBack = onBackClick,
            )
        },
        bottomBar = {
            if (totalSum != 0)
                FoodiesButton(
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp,
                        )
                        .fillMaxWidth(),
                    onClick = onClickCart,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_cart),
                        contentDescription = null,
                        tint = Color.White,
                    )
                    HorizontalSpacer(width = 8.dp)
                    Text(
                        text = "${uiState.totalSum} ₽",
                        color = Color.White,
                    )
                }
        },
        containerColor = Color.White
    ) {
        if (uiState.productList.isEmpty()) {
            EmptyList(
                value = "Ничего не нашлось :("
            )
        } else {
            if (uiState.searchText.isNotEmpty()) {
                FoodiesProductList(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = it.calculateTopPadding() + 16.dp
                        ),
                    uiState = uiState,
                    onClickProduct = onClickProduct,
                    onClickAddToCart = { product, i ->
                        viewModel.addToCart(product, i)
                    },
                    onClickRemoveToCart = { product ->
                        viewModel.removeFromCart(product)
                    },
                    searchText = uiState.searchText,
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier,
                        text = "Введите название блюда, \n" +
                                "которое ищете)",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0f, 0f, 0f, 0.6f),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}