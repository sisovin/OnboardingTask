package co.peanech.onboardingtask.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.peanech.onboardingtask.domain.model.Result
import co.peanech.onboardingtask.domain.usecase.CheckUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val checkUserUseCase: CheckUserUseCase,
) : ViewModel() {
    private val _loginResult = MutableStateFlow<Result<String>>(Result.Loading)
    val loginResult: StateFlow<Result<String>> = _loginResult.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = Result.Loading
            _loginResult.value = checkUserUseCase(email, password)
        }
    }
}
