package com.example.foodies.ui.screens.catalog

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.foodies.R
import com.example.foodies.common.FoodiesButton
import com.example.foodies.common.FoodiesProductList
import com.example.foodies.CatalogViewModel
import com.example.foodies.Filter
import com.example.foodies.common.EmptyList
import com.example.foodies.ui.screens.catalog.components.CatalogTopBar
import com.example.foodies.ui.screens.catalog.components.CategoryChip
import com.example.foodies.ui.screens.catalog.components.FilterCheckBox
import com.example.foodies.utils.HorizontalSpacer
import com.example.foodies.utils.VerticalSpace
import com.holix.android.bottomsheetdialog.compose.BottomSheetBehaviorProperties
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialog
import com.holix.android.bottomsheetdialog.compose.BottomSheetDialogProperties

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun CatalogScreen(
    modifier: Modifier = Modifier,
    viewModel: CatalogViewModel = hiltViewModel(),
    onClickProduct: (id: Int) -> Unit,
    onClickCart: () -> Unit,
    onClickSearch: () -> Unit,
) {
    val uiState by viewModel.catalogUiState.collectAsStateWithLifecycle()
    val totalSum by rememberUpdatedState(newValue = uiState.totalSum)

    var isVisibility by remember { mutableStateOf(false) }
    val bottomSheetDialogState by remember { mutableStateOf(BottomSheetBehaviorProperties.State.Collapsed) }
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val peekHeight = LocalDensity.current.run { screenHeight.toPx() }

    var filterCount by remember {
        mutableStateOf(0)
    }


    if (isVisibility) {
        BottomSheetDialog(
            onDismissRequest = { isVisibility = false },
            properties = BottomSheetDialogProperties(
                dismissWithAnimation = true,
                behaviorProperties = BottomSheetBehaviorProperties(
                    state = bottomSheetDialogState,
                    peekHeight = BottomSheetBehaviorProperties.PeekHeight(peekHeight.toInt()),
                )
            )
        ) {
            Column(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = 24.dp,
                            topEnd = 24.dp,
                        )
                    )
                    .background(Color.White)
                    .padding(
                        horizontal = 16.dp,
                        vertical = 32.dp,
                    )
                    ,
            ) {
                Text(
                    text = "Подобрать блюда",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Black,
                )
                VerticalSpace(height = 16.dp)
                Filter.entries.forEach { filter ->
                    FilterCheckBox(
                        filter = filter,
                        checked = viewModel.selectedFilters.value.contains(filter),
                        onCheckedChange = {
                            viewModel.toggleFilter(filter)
                        }
                    )
                    HorizontalDivider(
                        thickness = 0.2.dp,
                        color = Color.Gray,
                    )
                }
                VerticalSpace(height = 16.dp)
                FoodiesButton(
                    onClick = {
                        isVisibility = false
                        viewModel.getProducts()
                        filterCount = viewModel.selectedFilters.value.size
                    }

                ) {
                    Text(
                        text = "Готово"
                    )
                }
            }
        }

    }
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding(),
        topBar = {
            CatalogTopBar(
                onClickSearch = onClickSearch,
                onClickFilter = {
                   isVisibility = true
                },
                filterCount = filterCount
            )
        },
        contentColor = Color.White,
        containerColor = Color.White,
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
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding() + 10.dp
                )
                .background(Color.White)
        ) {
            LazyRow(
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 16.dp
                    )
            ) {
                items(
                    items = uiState.categoryList
                ) { category ->
                    CategoryChip(
                        isSelected = category.isSelect,
                        name = category.name,
                        onClickCategory = {
                            viewModel.changeSelectedCategory(category)
                        }
                    )
                }
            }
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
            if(uiState.productList.isEmpty()){
                EmptyList(
                    value = "Таких блюд нет :(\n Попробуйте изменить фильтры"
                )
            }else {
                FoodiesProductList(
                    uiState = uiState,
                    onClickProduct = onClickProduct,
                    onClickAddToCart = { product, i ->
                        viewModel.addToCart(product, i)
                    },
                    onClickRemoveToCart = { product ->
                        viewModel.removeFromCart(product)
                    },
                )
            }

        }
    }
}

