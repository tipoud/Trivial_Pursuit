package com.sandbox.data.di

import com.sandbox.data.api.CategoryApi
import com.sandbox.data.mapper.CategoryMapper
import com.sandbox.data.provider.provideRetrofit
import com.sandbox.data.repository.CategoryRepositoryImpl
import com.sandbox.domain.repository.CategoryRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Koin module for providing dependencies related to category data.
 *
 * This module defines how various dependencies are created and provided for the application.
 * It includes definitions for JSON parsing, Retrofit setup, API service creation, and repository implementation.
 */
val categoryDataModule = module {

    /**
     * Provides a singleton instance of [Moshi] for JSON parsing.
     */
    single<Moshi> {
        Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    }

    /**
     * Provides a singleton instance of [MoshiConverterFactory] for Retrofit.
     */
    single<MoshiConverterFactory> {
        MoshiConverterFactory.create(get())
    }

    /**
     * Provides a singleton instance of [Retrofit] configured with the provided [MoshiConverterFactory].
     */
    single<Retrofit> {
        provideRetrofit(get())
    }

    /**
     * Provides a singleton instance of [CategoryApi] created by Retrofit.
     */
    single<CategoryApi> {
        get<Retrofit>().create(CategoryApi::class.java)
    }

    /**
     * Provides a singleton instance of [CategoryMapper].
     */
    singleOf(::CategoryMapper)

    /**
     * Provides a singleton instance of [CategoryRepositoryImpl] and binds it to the [CategoryRepository] interface.
     */
    singleOf(::CategoryRepositoryImpl) bind CategoryRepository::class
}