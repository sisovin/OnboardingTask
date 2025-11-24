package co.peanech.onboardingtask.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.peanech.onboardingtask.domain.model.ErrorCode
import co.peanech.onboardingtask.domain.model.Result
import co.peanech.onboardingtask.domain.model.User
import co.peanech.onboardingtask.domain.usecase.GetUserUseCase
import co.peanech.onboardingtask.domain.usecase.InsertUserUseCase
import co.peanech.onboardingtask.presentation.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val insertUserUseCase: InsertUserUseCase,
) : ViewModel() {
    private val _signupResult = MutableStateFlow<Result<User>>(Result.Loading)
    val signupResult: StateFlow<Result<User>> = _signupResult.asStateFlow()

    private val _isExistEmail = MutableStateFlow<UiState<Boolean>>(UiState.Initial)
    val isExistEmail: StateFlow<UiState<Boolean>> = _isExistEmail.asStateFlow()

    fun signup(username: String, email: String, password: String) {
        viewModelScope.launch {
            _signupResult.value = Result.Loading
            val user = User(username = username, email = email, password = password)
            _signupResult.value = insertUserUseCase(user)
        }
    }

    fun checkIsEmailExist(email: String) {
        viewModelScope.launch {
            _isExistEmail.value = UiState.Loading
            when (val existUser = getUserUseCase(email)) {
                is Result.Success -> _isExistEmail.value = UiState.Success(true)
                is Result.Error -> {
                    _isExistEmail.value =
                        if (existUser.code == ErrorCode.NOT_FOUND) UiState.Success(false) else UiState.Error(
                            existUser.message
                        )
                }

                else -> _isExistEmail.value = UiState.Loading
            }
        }
    }
}
