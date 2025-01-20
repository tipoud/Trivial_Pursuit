package com.sandbox.domain.repository

import com.sandbox.domain.model.CategoryDomain


/**
 * Repository interface for fetching category data.
 *
 * This interface defines the contract for data operations related to categories. It provides a method to fetch
 * a list of categories, returning the result as a [Result] containing a list of [CategoryDomain] objects.
 *
 * The repository pattern in Clean Architecture serves as an abstraction layer between the domain and data layers,
 * allowing the domain layer to interact with data sources without needing to know the details of data retrieval or storage.
 */
interface CategoryRepository {

    /**
     * Fetches a list of categories.
     *
     * This method is responsible for retrieving category data, which could come from various data sources such as
     * a remote API, local database, or cache. The result is returned as a [Result] containing a list of [CategoryDomain] objects.
     *
     * @return A [Result] containing a list of [CategoryDomain] objects if successful, or an error if the operation fails.
     */
    suspend fun getCategories(): Result<List<CategoryDomain>>
}


/**
 * The repository interface plays a crucial role in Clean Architecture by defining the contract for data operations.
 *
 * In Clean Architecture, the repository interface is part of the domain layer and serves as an abstraction layer
 * between the domain and data layers. It allows the domain layer to interact with data sources without needing to know
 * the details of data retrieval or storage.
 *
 * By defining a clear contract for data operations, the repository interface promotes a clean separation of concerns,
 * making the codebase more modular, testable, and maintainable.
 *
 * The repository interface can be implemented by various concrete classes that handle data operations from different
 * sources (e.g., network, database, cache). This flexibility allows for easy swapping of data sources without affecting
 * the domain layer.
 */