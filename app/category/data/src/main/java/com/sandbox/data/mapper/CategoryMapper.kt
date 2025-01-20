package com.sandbox.data.mapper

import com.sandbox.data.response.CategoryResponse
import com.sandbox.domain.model.CategoryDomain

/**
 * A mapper class responsible for converting API responses to domain models.
 *
 * This class provides methods to map data from the format provided by the data source (e.g., API response)
 * to the format required by the domain layer. This ensures that the domain layer works with clean, domain-specific models.
 */
class CategoryMapper {

    /**
     * Maps a [CategoryResponse] to a list of [CategoryDomain] domain models.
     *
     * This method takes the response from the API, extracts the relevant data, and converts it into a list
     * of [CategoryDomain] objects that can be used by the domain layer.
     *
     * Note: In a more robust implementation, this method could potentially return an error if the mapping is impossible
     * due to issues with the API response (e.g., missing or malformed data). However, this example does not handle such errors.
     *
     * @param response The response from the API containing category data.
     * @return A list of [CategoryDomain] objects.
     */
    fun map(response: CategoryResponse): List<CategoryDomain> {
        return response.triviaCategories.map {
            CategoryDomain(it.id, it.name)
        }
    }
}