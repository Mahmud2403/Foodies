package com.example.foodies.data.repository

import com.example.foodies.data.network_source.FoodiesApi
import com.example.foodies.data.network_source.model.toTag
import com.example.foodies.domain.model.Tag
import com.example.foodies.domain.repository.TagRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TagRepositoryImpl @Inject constructor(
    private val api: FoodiesApi
): TagRepository {
    override suspend fun getTag(): Flow<List<Tag>> = flow {
        emit(api.getTags().map { it.toTag() })
    }

}