package com.sandbox.data.repository

import co.touchlab.kermit.Logger
import com.sandbox.data.api.CategoryApi
import com.sandbox.data.mapper.CategoryMapper
import com.sandbox.domain.model.CategoryDomain
import com.sandbox.domain.repository.CategoryRepository

/**
 * Implementation of the [CategoryRepository] interface.
 *
 * This class is responsible for fetching category data from a remote API and mapping it to the domain model.
 *
 * @property categoryApi The API service used to fetch category data.
 * @property mapper The mapper used to convert API responses to domain models.
 * @param logger The logger used for logging debug information.
 */
class CategoryRepositoryImpl(
    private val categoryApi: CategoryApi,
    private val mapper: CategoryMapper,
    logger: Logger
) : CategoryRepository {

    private val log = logger.withTag("CategoryRepository")

    /**
     * Fetches a list of categories from the remote API.
     *
     * This method makes a network call to fetch category data, maps the response to domain models,
     * and returns the result.
     *
     * @return A [Result] containing a list of [CategoryDomain] objects if successful, or an error if the operation fails.
     */
    override suspend fun getCategories(): Result<List<CategoryDomain>> {
        log.d { "Fetching categories" }
        return kotlin.runCatching {
            categoryApi.getCategoryResponse()
                .let(mapper::map)
        }
    }
}


/**
 * The repository pattern is a key component in Clean Architecture.
 *
 * In Clean Architecture, the repository serves as an abstraction layer that separates the domain and data layers.
 * It provides a clean API for data access, allowing the domain layer to interact with data sources without
 * needing to know the details of data retrieval or storage.
 *
 * The repository handles data operations, such as fetching, saving, and updating data, and can aggregate data
 * from multiple sources (e.g., network, database, cache). This promotes a clear separation of concerns,
 * making the codebase more modular, testable, and maintainable.
 *
 * The mapper is used to convert data from the format provided by the data source (e.g., API response) to the
 * format required by the domain layer. This ensures that the domain layer works with clean, domain-specific models.
 *
 * Error handling is also managed within the repository. By using constructs like [Result], the repository can
 * encapsulate success and failure scenarios, providing a consistent way to handle errors and propagate them
 * to the domain layer.
 */