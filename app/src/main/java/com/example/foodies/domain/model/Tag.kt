package com.example.foodies.domain.model

data class Tag(
    val id: Int,
    val name: String,
    val isSelect: Boolean = false,
)