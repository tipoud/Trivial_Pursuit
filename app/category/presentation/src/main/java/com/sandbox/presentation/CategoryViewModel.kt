package com.sandbox.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.sandbox.domain.GetCategoryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for managing category data and handling user intents.
 *
 * This ViewModel follows the MVI (Model-View-Intent) pattern, which promotes a unidirectional data flow
 * and a clear separation of concerns. The ViewModel interacts with the use case to fetch data and updates
 * the view state accordingly. It also handles user intents and performs the necessary actions.
 *
 * @property logger The logger used for logging debug information.
 * @property getCategory The use case for fetching category data.
 */
class CategoryViewModel(logger: Logger, private val getCategory: GetCategoryUseCase) : ViewModel() {

    private val log = logger.withTag("CategoryViewModel")

    private val _viewState = MutableStateFlow(CategoryViewState(emptyList()))
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            getCategory().onSuccess {
                _viewState.value = _viewState.value.copy(categories = it)
            }
        }
    }

    /**
     * Handles user intents and performs the necessary actions.
     *
     * This method processes the user intents and updates the view state or performs other actions
     * based on the intent type.
     *
     * @param intent The user intent to be processed.
     */
    fun onIntent(intent: CategoryIntent) {
        when (intent) {
            is CategoryIntent.OnCategoryClicked -> {
                log.d { "Category clicked ${intent.categoryId}" }
            }
            else -> {}
        }
    }
}



/**
 * The MVI (Model-View-Intent) pattern is an architectural pattern that promotes a unidirectional data flow
 * and a clear separation of concerns. It consists of three main components:
 *
 * - Model: Represents the state of the view. In this case, the [CategoryViewState] represents the state of the view.
 * - View: Represents the UI that displays the state and sends user intents. The View observes the [viewState] and updates
 *   the UI accordingly.
 * - Intent: Represents the user's actions or events. The [CategoryIntent] class defines the possible user actions.
 *
 * The ViewModel acts as the mediator between the Model and the View. It processes the user intents, interacts with the
 * use case to fetch data, and updates the view state. This promotes a unidirectional data flow, making the codebase
 * more predictable, testable, and maintainable.
 */