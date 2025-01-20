package com.sandbox.presentation

sealed interface CategoryIntent {

    data class OnCategoryClicked(val categoryId: String) : CategoryIntent
}
