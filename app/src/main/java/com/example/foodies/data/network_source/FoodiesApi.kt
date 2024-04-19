package com.example.foodies.data.network_source

import com.example.foodies.data.network_source.model.CategoryDto
import com.example.foodies.data.network_source.model.ProductDto
import com.example.foodies.data.network_source.model.TagDto
import retrofit2.http.GET

interface FoodiesApi {

    @GET("Categories.json")
    suspend fun getCategories(): List<CategoryDto>

    @GET("Tags.json")
    suspend fun getTags(): List<TagDto>

    @GET("Products.json")
    suspend fun getProducts(): List<ProductDto>
}