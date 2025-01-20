package com.sandbox.data.response

import com.squareup.moshi.Json

/**
 * Data class representing the response from the API containing category data.
 *
 * This class is used to parse the JSON response from the API. It contains a list of [TriviaCategoryDTO] objects,
 * which represent individual categories.
 *
 * @property triviaCategories The list of trivia categories returned by the API.
 */
data class CategoryResponse(
    @Json(name = "trivia_categories") val triviaCategories: List<TriviaCategoryDTO>
)

/**
 * Data Transfer Object (DTO) representing a trivia category.
 *
 * This class is used to parse individual category data from the API response. It contains the ID and name of the category.
 *
 * @property id The unique identifier of the category.
 * @property name The name of the category.
 */
data class TriviaCategoryDTO(
    val id: Int,
    val name: String
)