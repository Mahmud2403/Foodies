package com.example.foodies.data.network_source.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep
import com.example.foodies.domain.model.Product
import com.google.gson.annotations.SerializedName

@Keep
@Serializable
data class ProductDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("category_id")
    val categoryId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("price_current")
    val priceCurrent: Int,
    @SerializedName("price_old")
    val priceOld: Int?,
    @SerializedName("measure")
    val measure: Int,
    @SerializedName("measure_unit")
    val measureUnit: String,
    @SerializedName("carbohydrates_per_100_grams")
    val carbohydratesPer100Grams: Double,
    @SerializedName("energy_per_100_grams")
    val energyPer100Grams: Double,
    @SerializedName("fats_per_100_grams")
    val fatsPer100Grams: Double,
    @SerializedName("proteins_per_100_grams")
    val proteinsPer100Grams: Double,
    @SerializedName("tag_ids")
    val tagIds: List<Int>?
)

fun ProductDto.toProduct() = Product(
    carbohydratesPer100Grams = carbohydratesPer100Grams,
    categoryId = categoryId,
    description = description,
    energyPer100Grams = energyPer100Grams,
    fatsPer100Grams = fatsPer100Grams,
    id = id,
    image = image,
    measure = measure,
    measureUnit = measureUnit,
    name = name,
    priceCurrent = priceCurrent,
    priceOld = priceOld,
    proteinsPer100Grams = proteinsPer100Grams,
    tagIds = tagIds

)