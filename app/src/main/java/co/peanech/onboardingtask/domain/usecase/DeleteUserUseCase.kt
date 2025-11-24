package co.peanech.onboardingtask.domain.usecase

import co.peanech.onboardingtask.domain.model.Result
import co.peanech.onboardingtask.domain.model.User
import co.peanech.onboardingtask.domain.repository.UserRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User): Result<Unit> {
        return userRepository.deleteUser(user)
    }
}
