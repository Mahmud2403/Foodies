package com.example.foodies

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodies.data.network_source.collectAsResult
import com.example.foodies.domain.model.Cart
import com.example.foodies.domain.model.Category
import com.example.foodies.domain.model.Product
import com.example.foodies.domain.model.Tag
import com.example.foodies.domain.repository.CategoryRepository
import com.example.foodies.domain.repository.ProductRepository
import com.example.foodies.domain.repository.TagRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository,
    private val tagRepository: TagRepository,
) : ViewModel() {

    private var _catalogUiState = MutableStateFlow(CatalogUiState())
    val catalogUiState = _catalogUiState.asStateFlow()

    private val selectedCategory = MutableStateFlow<Category?>(null)
    val selectedFilters = mutableStateOf(listOf<Filter>())


    init {
        getCategories()
        getTags()
    }

    //Получение категорий
    private fun getCategories() {
        viewModelScope.launch {
            categoryRepository.getCategory().collectAsResult(
                onSuccess = { category ->
                    val categoryList = category.mapIndexed { index, categoryItem ->
                        categoryItem.copy(isSelect = index == 0)
                    }
                    _catalogUiState.update { currentState ->
                        currentState.copy(
                            categoryList = categoryList
                        )
                    }
                    selectedCategory.value = categoryList.firstOrNull()
                    getProducts()
                }
            )
        }
    }

    //Получение всех продуктов
    fun getProducts() {
        viewModelScope.launch {
            productRepository.getProducts().collectAsResult(
                onSuccess = { products ->
                    val filteredProducts = if (selectedCategory.value != null) {
                        products.filter { it.categoryId == selectedCategory.value!!.id }
                    } else {
                        products
                    }.filter { product ->
                        product.tagIds?.let { tagIds ->
                            if (selectedFilters.value.isEmpty()) {
                                true
                            } else {
                                selectedFilters.value.map { it.id }.all { tagIds.contains(it) }
                            }
                        } ?: false
                    }

                    _catalogUiState.update { currentState ->
                        currentState.copy(
                            productList = filteredProducts,
                            isLoading = false,
                        )
                    }
                },
                onLoading = {
                    _catalogUiState.update { currentState ->
                        currentState.copy(
                            isLoading = true,
                        )
                    }
                },
                onError = { ex, _ ->
                    _catalogUiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            error = ex.toString()
                        )
                    }
                }
            )
        }
    }

    //Получние тегов
    private fun getTags() {
        viewModelScope.launch {
            tagRepository.getTag().collectAsResult(
                onSuccess = { tag ->
                    _catalogUiState.update { currentState ->
                        currentState.copy(
                            tagList = tag
                        )
                    }
                }
            )
        }
    }

    //Фильтрация продуктов по категориям
    fun changeSelectedCategory(category: Category) {
        selectedCategory.value = category
        _catalogUiState.update { currentState ->
            val categoryList = currentState.categoryList.map { it.copy(isSelect = it == category) }
            currentState.copy(
                categoryList = categoryList
            )
        }
        getProducts()
    }

    //Список детального пролукта
    fun getDetailProduct(id: Int): Product =
        _catalogUiState.value.productList.find {
            it.id == id
        }!!

    //Добавление продукта в корзину
    fun addToCart(product: Product, quantity: Int) {
        val updatedCartList = _catalogUiState.value.cartList.toMutableList()

        val existingCartItem = updatedCartList.find { it.product.id == product.id }

        if (existingCartItem != null) {
            val updatedCartItem = existingCartItem.copy(quantity = existingCartItem.quantity + 1)
            updatedCartList.remove(existingCartItem)
            updatedCartList.add(updatedCartItem)
        } else {
            updatedCartList.add(Cart(product, quantity))
        }

        val totalSum = updatedCartList.sumOf { it.product.priceCurrent / 100 * it.quantity }

        _catalogUiState.update { currentState ->
            currentState.copy(
                cartList = updatedCartList,
                totalSum = totalSum
            )
        }
    }

    //Удаление продукта с корзины
    fun removeFromCart(product: Product) {
        val updatedCartList = _catalogUiState.value.cartList.toMutableList().apply {
            val existingCartItem = find { it.product.id == product.id }
            existingCartItem?.let {
                if (it.quantity > 1) {
                    val updatedCartItem = it.copy(quantity = it.quantity - 1)
                    remove(it)
                    add(updatedCartItem)
                } else {
                    remove(it)
                }
            }
        }

        val totalSum = updatedCartList.sumBy { it.product.priceCurrent / 100 * it.quantity }

        _catalogUiState.update { currentState ->
            currentState.copy(
                cartList = updatedCartList,
                totalSum = totalSum
            )
        }
    }

    //Поиск продуктов
    private fun searchProduct() {
        viewModelScope.launch {
            if (_catalogUiState.value.searchText.isNotEmpty())
                productRepository.getProducts().collectAsResult(
                    onSuccess = { products ->

                        val search = products.filter {
                            it.name.contains(_catalogUiState.value.searchText, ignoreCase = true)
                        }

                        _catalogUiState.update { currentState ->
                            currentState.copy(
                                productList = search,
                                isLoading = false,
                            )
                        }
                    },
                    onLoading = {
                        _catalogUiState.update { currentState ->
                            currentState.copy(
                                isLoading = true,
                            )
                        }
                    },
                    onError = { ex, _ ->
                        _catalogUiState.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                error = ex.toString()
                            )
                        }
                    }
                )
        }
    }

    //Изменение ввода слов в поисковик
    fun changeSearchText(newValue: String) {
        _catalogUiState.update { currentState ->
            currentState.copy(
                searchText = newValue,
            )
        }
        searchProduct()
    }


    fun toggleFilter(filter: Filter) {
        if (selectedFilters.value.contains(filter)) {
            selectedFilters.value -= filter
        } else {
            selectedFilters.value += filter
        }
    }

}

data class CatalogUiState(
    val productList: List<Product> = emptyList(),
    val categoryList: List<Category> = emptyList(),
    val tagList: List<Tag> = emptyList(),
    val cartList: List<Cart> = emptyList(),
    var totalSum: Int = 0,
    val searchText: String = "",
    val isLoading: Boolean = false,
    val error: String = "",
)

enum class Filter(val value: String, val id: Int) {
    Meatless("Без мяса", 2),
    Sharp("Острое", 3),
    Discount("Со скидкой", 4),
}