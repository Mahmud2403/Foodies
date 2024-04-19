package com.example.foodies.domain.model


data class Cart(
    val product: Product,
    var quantity: Int,
)

data class Product(
    val id: Int,
    val categoryId: Int,
    val name: String,
    val description: String,
    val image: String,
    val priceCurrent: Int,
    val priceOld: Int?,
    val measure: Int,
    val measureUnit: String?,
    val carbohydratesPer100Grams: Double,
    val energyPer100Grams: Double,
    val fatsPer100Grams: Double,
    val proteinsPer100Grams: Double,
    val tagIds: List<Int>?
)