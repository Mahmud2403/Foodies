package com.example.foodies.data.network_source.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep
import com.example.foodies.domain.model.Category

@Keep
@Serializable
data class CategoryDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String
)


fun CategoryDto.toCategory(): Category = Category(
    id = id,
    name = name,
)