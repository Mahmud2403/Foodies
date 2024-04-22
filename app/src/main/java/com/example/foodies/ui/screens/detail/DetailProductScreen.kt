package com.example.foodies.ui.screens.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.foodies.R
import com.example.foodies.common.FoodiesButton
import com.example.foodies.domain.model.Product
import com.example.foodies.MainViewModel
import com.example.foodies.ui.screens.detail.components.ProductCharacteristics
import com.example.foodies.utils.HorizontalSpacer
import com.example.foodies.utils.VerticalSpace
import com.example.foodies.utils.clickableWithRipple
import com.example.foodies.utils.innerShadow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailProductScreen(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    product: Product,
    viewModel: MainViewModel,
) {
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding(),
        topBar = {
            Box(
                modifier = Modifier
                    .padding(
                        start = 26.dp,
                        top = 26.dp,
                    )
                    .clickableWithRipple(
                        onClick = onClickBack,
                    )
                    .clip(CircleShape)
                    .innerShadow(
                        shape = CircleShape,
                        color = Color(0xFFF5F5F5),
                        offsetX = (-1).dp,
                        offsetY = (-1).dp,
                    )
                    .innerShadow(
                        shape = CircleShape,
                        color = Color(0xFFF5F5F5),
                        offsetX = 1.dp,
                        offsetY = (-1).dp,
                    )
            ) {
                Icon(
                    modifier = Modifier
                        .padding(10.dp),
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        },
        bottomBar = {
            FoodiesButton(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp,
                    )
                    .fillMaxWidth(),
                onClick = {
                    viewModel.addToCart(product, 1)
                    onClickBack()
                },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_cart),
                    contentDescription = null,
                    tint = Color.White,
                )
                HorizontalSpacer(width = 8.dp)
                Text(
                    text = "В корзину за ${product.priceCurrent.toString().dropLast(2)} ₽",
                    color = Color.White,
                )
            }
        },
        containerColor = Color.White,
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding() + 10.dp,
                )
                .fillMaxSize()
                .verticalScroll(scrollState),
        ) {
            Image(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                painter = painterResource(id = R.drawable.foodies),
                contentDescription = null,
            )
            VerticalSpace(height = 24.dp)
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                text = product.name,
                style = MaterialTheme.typography.headlineLarge,
                color = Color.Black,
            )
            VerticalSpace(height = 8.dp)
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                text = product.description,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black.copy(0.5f)
            )
            VerticalSpace(height = 24.dp)
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth()
            )
            ProductCharacteristics(
                name = "Вес",
                meaning = "${product.measure} ${product.measureUnit}",
            )
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth()
            )
            ProductCharacteristics(
                name = "Энерг. ценность",
                meaning = "${product.energyPer100Grams} калл",
            )
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth()
            )
            ProductCharacteristics(
                name = "Белки",
                meaning = "${product.proteinsPer100Grams} г",
            )
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth()
            )
            ProductCharacteristics(
                name = "Жиры",
                meaning = "${product.fatsPer100Grams} г",
            )
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth()
            )
            ProductCharacteristics(
                name = "Углеводы",
                meaning = "${product.carbohydratesPer100Grams} г",
            )
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}