package com.example.foodies.data.di

import com.example.foodies.data.repository.CategoryRepositoryImpl
import com.example.foodies.data.repository.ProductRepositoryImpl
import com.example.foodies.data.repository.TagRepositoryImpl
import com.example.foodies.domain.repository.CategoryRepository
import com.example.foodies.domain.repository.ProductRepository
import com.example.foodies.domain.repository.TagRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepoModule {

    @Binds
    abstract fun bindCategoryRepository(repo: CategoryRepositoryImpl): CategoryRepository

    @Binds
    abstract fun bindProductsRepository(repo: ProductRepositoryImpl): ProductRepository

    @Binds
    abstract fun bindTagsRepository(repo: TagRepositoryImpl): TagRepository
}