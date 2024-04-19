package com.example.foodies.common

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodies.R
import com.example.foodies.TAG
import com.example.foodies.domain.model.Product
import com.example.foodies.CatalogUiState
import com.example.foodies.Filter
import com.example.foodies.ui.screens.catalog.models.Digit
import com.example.foodies.ui.screens.catalog.models.compareTo
import com.example.foodies.utils.clickableWithRipple

@Preview
@Composable
private fun ProductCardPreview() {
    Column {
        ProductCard(
            name = "Том Ян",
            measure = "500 г",
            onClickProduct = { /*TODO*/ },
            count = 0,
            tagIds = null,
        )
        ProductCard(
            name = "Название блюда",
            measure = "500 г",
            onClickProduct = { /*TODO*/ },
            count = 5,
            tagIds = listOf(2)
        )
    }
}

@Composable
fun FoodiesProductList(
    modifier: Modifier = Modifier,
    uiState: CatalogUiState,
    onClickProduct: (Int) -> Unit,
    onClickAddToCart: (Product, Int) -> Unit,
    onClickRemoveToCart: (Product) -> Unit,
    searchText: String = "",
) {
    LazyVerticalGrid(
        modifier = modifier
            .padding(horizontal = 12.dp)
            .fillMaxSize(),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            items = uiState.productList,
        ) { product ->
            val cartItem = uiState.cartList.find { it.product.id == product.id }
            val count = cartItem?.quantity ?: 0
            Log.d(TAG, "FoodiesProductList: ${product.tagIds}")
            ProductCard(
                name = product.name,
                measure = "${product.measure} ${product.measureUnit}",
                onClickProduct = {
                    onClickProduct(product.id)
                },
                count = count,
                buttonPay = {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        onClick = {
                            onClickAddToCart(product, 1)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        )
                    ) {
                        Text(
                            text = "${
                                product.priceCurrent.toString().dropLast(2)
                            } ₽",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        if (product.priceOld != null)
                            Text(
                                text = "${
                                    product.priceOld.toString().dropLast(2)
                                } ₽",
                                style = MaterialTheme.typography.labelMedium,
                                textDecoration = TextDecoration.LineThrough,
                                color = Color.Black.copy(0.5f),
                                maxLines = 1,
                            )
                    }
                },
                buttonCount = {
                    cartItem?.let { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    vertical = 16.dp,
                                    horizontal = 12.dp
                                ),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.White)
                                    .clickableWithRipple(
                                        onClick = {
                                            onClickRemoveToCart(product)
                                        }
                                    )
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .padding(8.dp),
                                    painter = painterResource(id = R.drawable.ic_minus),
                                    contentDescription = null,
                                    tint = Color(0xFFF15412)
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .animateContentSize()
                                    .padding(horizontal = 3.dp)
                                    .size(24.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                item.quantity.toString()
                                    .mapIndexed { index, c ->
                                        Digit(
                                            c,
                                            item.quantity,
                                            index
                                        )
                                    }
                                    .forEach { digit ->
                                        AnimatedContent(
                                            targetState = digit,
                                            transitionSpec = {
                                                if (targetState > initialState) {
                                                    slideInVertically { -it } togetherWith slideOutVertically { it }
                                                } else {
                                                    slideInVertically { it } togetherWith slideOutVertically { -it }
                                                }
                                            }, label = ""
                                        ) { digit ->
                                            Text(
                                                "${digit.digitChar}",
                                                textAlign = TextAlign.Center,
                                                color = Color.Black,
                                                fontSize = 14.sp,
                                            )
                                        }
                                    }
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.White)
                                    .clickableWithRipple(
                                        onClick = {
                                            onClickAddToCart(
                                                product,
                                                item.quantity + 1
                                            )
                                        }
                                    )
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .padding(8.dp),
                                    painter = painterResource(id = R.drawable.ic_plus),
                                    contentDescription = null,
                                    tint = Color(0xFFF15412)
                                )
                            }
                        }
                    }
                },
                tagIds = product.tagIds,
                searchText = searchText,
            )
        }
    }
}


@Composable
fun ProductCard(
    modifier: Modifier = Modifier,
    name: String,
    measure: String,
    searchText: String = "",
    onClickProduct: () -> Unit,
    count: Int = 0,
    buttonPay: @Composable (() -> Unit)? = null,
    buttonCount: @Composable (() -> Unit)? = null,
    tagIds: List<Int>?,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFF0EEEE))
            .clickable(
                onClick = onClickProduct
            ),
    ) {
        Box {
            Row(
                modifier = Modifier.padding(8.dp)
            ) {
                tagIds?.forEach { tagId ->
                    val drawableRes = when (tagId) {
                        2 -> R.drawable.ic_life
                        3 -> R.drawable.ic_pepper
                        4 -> R.drawable.ic_discount
                        else -> null
                    }
                    if (drawableRes != null) {
                        Image(
                            modifier = Modifier.padding(end = 8.dp),
                            painter = painterResource(id = drawableRes),
                            contentDescription = null,
                        )
                    }
                }
            }
            Image(
                modifier = Modifier
                    .size(170.dp),
                painter = painterResource(id = R.drawable.foodies),
                contentDescription = null,
            )
        }
        Text(
            modifier = Modifier
                .padding(
                    start = 12.dp,
                    top = 12.dp,
                    bottom = 4.dp,
                ),
            text = highlightText(searchText, name),
            color = Color.Black,
            minLines = 3,
        )
        Text(
            modifier = Modifier
                .padding(
                    start = 12.dp,
                    bottom = 12.dp,
                ),
            text = measure,
            color = Color.Black.copy(0.5f)
        )
        if (count == 0) {
            buttonPay?.invoke()
        } else {
            buttonCount?.invoke()
        }
    }
}

