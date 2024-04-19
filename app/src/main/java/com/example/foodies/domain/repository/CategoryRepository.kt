package com.example.foodies.domain.repository

import com.example.foodies.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getCategory(): Flow<List<Category>>
}