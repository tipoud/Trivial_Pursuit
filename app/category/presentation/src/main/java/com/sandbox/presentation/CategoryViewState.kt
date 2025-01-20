package com.sandbox.presentation

import androidx.compose.runtime.Immutable
import com.sandbox.domain.model.CategoryDomain

@Immutable
data class CategoryViewState(

    val categories: List<CategoryDomain>

)
