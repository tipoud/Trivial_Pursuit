package com.sandbox.domain

import co.touchlab.kermit.Logger
import com.sandbox.domain.model.CategoryDomain
import com.sandbox.domain.repository.CategoryRepository

/**
 * Use case class for fetching categories.
 *
 * This class represents a use case in the domain layer, responsible for executing the business logic to fetch
 * category data. It interacts with the [CategoryRepository] to retrieve the data and handles any necessary
 * logging or additional processing.
 *
 * @property repository The repository used to fetch category data.
 * @param logger The logger used for logging debug information.
 */
class GetCategoryUseCase(private val repository: CategoryRepository, logger: Logger) {

    private val log = logger.withTag("GetCategoryUseCase")

    /**
     * Invokes the use case to fetch categories.
     *
     * This method calls the [CategoryRepository.getCategories] method to retrieve category data and logs the operation.
     * It returns the result as a [Result] containing a list of [CategoryDomain] objects.
     *
     * @return A [Result] containing a list of [CategoryDomain] objects if successful, or an error if the operation fails.
     */
    suspend operator fun invoke(): Result<List<CategoryDomain>> {
        log.d("GetCategoryUseCase")
        return repository.getCategories()
    }
}


/**
 * The use case plays a crucial role in Clean Architecture by encapsulating a specific piece of business logic.
 *
 * In Clean Architecture, use cases are part of the domain layer and represent the application's business rules.
 * They orchestrate the flow of data to and from the entities, repositories, and other domain objects.
 *
 * The use case interacts with the repository to fetch data, ensuring that the domain layer remains decoupled from
 * the data layer. This promotes a clear separation of concerns, making the codebase more modular, testable, and maintainable.
 *
 * By encapsulating business logic within use cases, we can ensure that the logic is reusable and easily testable.
 * Use cases also help in maintaining a single responsibility principle, as each use case is responsible for a specific
 * piece of functionality.
 */