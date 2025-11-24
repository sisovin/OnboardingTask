package co.peanech.onboardingtask.domain.repository

import co.peanech.onboardingtask.domain.model.Result
import co.peanech.onboardingtask.domain.model.User

interface UserRepository {
    suspend fun insertUser(user: User): Result<User>
    suspend fun checkIsValidUser(email: String, password: String): Result<String>
    suspend fun getUserByEmail(email: String): Result<User>
    suspend fun deleteUser(user: User): Result<Unit>
}
