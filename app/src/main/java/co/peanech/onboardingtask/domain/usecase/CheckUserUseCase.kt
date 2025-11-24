package co.peanech.onboardingtask.domain.usecase

import co.peanech.onboardingtask.domain.model.Result
import co.peanech.onboardingtask.domain.repository.UserRepository
import javax.inject.Inject

class CheckUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<String> {
        return userRepository.checkIsValidUser(email, password)
    }
}
