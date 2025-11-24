package co.peanech.onboardingtask.presentation.model

sealed interface UiState<out T> {
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val message: String?) : UiState<Nothing>
    data object Loading : UiState<Nothing>
    data object Initial : UiState<Nothing>
}
