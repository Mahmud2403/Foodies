package com.example.foodies.domain.repository

import com.example.foodies.domain.model.Tag
import kotlinx.coroutines.flow.Flow


interface TagRepository {
    suspend fun getTag(): Flow<List<Tag>>
}