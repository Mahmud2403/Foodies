package com.example.foodies.data.repository

import com.example.foodies.data.network_source.FoodiesApi
import com.example.foodies.data.network_source.model.toProduct
import com.example.foodies.domain.model.Product
import com.example.foodies.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: FoodiesApi
): ProductRepository {
    override suspend fun getProducts(): Flow<List<Product>> = flow {
        emit(api.getProducts().map { it.toProduct() })
    }
}