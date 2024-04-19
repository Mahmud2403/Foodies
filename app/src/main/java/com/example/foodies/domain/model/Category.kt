package com.example.foodies.domain.model

import com.example.foodies.ui.screens.catalog.models.Chip

data class Category(
    override val id: Int,
    override val name: String,
    override val isSelect: Boolean = false,
) : Chip