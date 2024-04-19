package com.example.foodies.data.network_source.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep
import com.example.foodies.domain.model.Tag

@Keep
@Serializable
data class TagDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String
)

fun TagDto.toTag() = Tag(
    id = id,
    name = name,
)