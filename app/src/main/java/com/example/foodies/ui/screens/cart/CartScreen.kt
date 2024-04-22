package com.example.foodies.ui.screens.cart

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.foodies.R
import com.example.foodies.TAG
import com.example.foodies.common.FoodiesButton
import com.example.foodies.ui.screens.cart.components.CartItemCard
import com.example.foodies.MainViewModel
import com.example.foodies.common.EmptyList
import com.example.foodies.utils.HorizontalSpacer
import com.example.foodies.utils.clickableWithRipple

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    onClickOrder: () -> Unit,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val uiState by viewModel.catalogUiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding(),
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .shadow(
                        elevation = 12.dp
                    ),
                title = {
                    Text(
                        modifier = Modifier
                            .padding(16.dp),
                        text = "Корзина"
                    )
                },
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .padding(16.dp)
                            .clickableWithRipple(
                                onClick = onClickBack
                            ),
                        painter = painterResource(id = R.drawable.ic_arrow_left),
                        contentDescription = null,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color(0xFFF15412),
                )
            )
        },
        bottomBar = {
            if (uiState.totalSum != 0) {
                FoodiesButton(
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp,
                        )
                        .fillMaxWidth(),
                    onClick = onClickOrder,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_cart),
                        contentDescription = null,
                        tint = Color.White,
                    )
                    HorizontalSpacer(width = 8.dp)
                    Text(
                        text = "Заказать за ${uiState.totalSum} ₽",
                        color = Color.White,
                    )
                }
            }
        },
        containerColor = Color.White,
    ) {
        if (uiState.totalSum == 0) {
            EmptyList(
                value = "Пусто, выберите блюда\n в каталоге :)"
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(top = it.calculateTopPadding())
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(
                    items = uiState.cartList.sortedBy { it.product.id },
                    key = { cart -> cart.product.id }
                ) { cart ->
                    CartItemCard(
                        productName = cart.product.name,
                        priceTotal = cart.product.priceCurrent.toString().dropLast(2)
                            .toInt() * cart.quantity,
                        priceOld = if (cart.product.priceOld != null) cart.product.priceOld.toString()
                            .dropLast(2).toInt().times(cart.quantity) else null,
                        count = cart.quantity,
                        onClickMinus = { count ->
                            viewModel.removeFromCart(cart.product)
                        },
                        onClickPlus = { count ->
                            viewModel.addToCart(cart.product, count + 1)
                        }
                    )
                    HorizontalDivider(
                        thickness = 0.4.dp,
                        color = Color.Gray,
                    )
                    Log.d(
                        TAG,
                        "id: ${cart.product.id}, count: ${cart.quantity}, name: ${cart.product.name}"
                    )
                }
            }
        }
    }
}
