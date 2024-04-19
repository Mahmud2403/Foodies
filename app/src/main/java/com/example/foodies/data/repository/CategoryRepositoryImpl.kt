package com.example.foodies.data.repository

import com.example.foodies.data.network_source.FoodiesApi
import com.example.foodies.data.network_source.model.toCategory
import com.example.foodies.domain.model.Category
import com.example.foodies.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val api: FoodiesApi
): CategoryRepository {
    override suspend fun getCategory(): Flow<List<Category>> = flow {
        emit(api.getCategories().map { it.toCategory() })
    }
}