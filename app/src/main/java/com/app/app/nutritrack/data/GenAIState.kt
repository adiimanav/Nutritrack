package com.fit2081.aditya_33520070.nutritrack.data

sealed interface GenAIState {
    /**
     * Empty state when the screen is first shown
     */
    object Initial: GenAIState

    /**
     * Still loading
     */
    object Loading: GenAIState

    /**
     * Text has been generated
     */
    data class Success(val outputText: String): GenAIState

    /**
     * There was an error generating text
     */
    data class Error(val errorMessage: String): GenAIState

}