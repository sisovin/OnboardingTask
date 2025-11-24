package co.peanech.onboardingtask.domain.usecase

import co.peanech.onboardingtask.domain.model.Result
import co.peanech.onboardingtask.domain.model.User
import co.peanech.onboardingtask.domain.repository.UserRepository
import javax.inject.Inject

class InsertUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(user: User): Result<User> {
        return userRepository.insertUser(user)
    }
}
