package co.peanech.onboardingtask.data.repository

import co.peanech.onboardingtask.data.dao.UserDao
import co.peanech.onboardingtask.data.mapper.toUser
import co.peanech.onboardingtask.data.mapper.toUserEntity
import co.peanech.onboardingtask.domain.model.ErrorCode
import co.peanech.onboardingtask.domain.model.Result
import co.peanech.onboardingtask.domain.model.User
import co.peanech.onboardingtask.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun insertUser(user: User): Result<User> = try {
        val existUser = userDao.getUserByEmail(user.email)
        if (existUser != null) {
            Result.Error("이미 존재하는 이메일입니다.", ErrorCode.EMAIL)
        } else {
            userDao.insertUser(user.toUserEntity())
            Result.Success(user)
        }
    } catch (e: Exception) {
        Result.Error(e.message)
    }

    override suspend fun checkIsValidUser(email: String, password: String): Result<String> = try {
        val userByEmail = userDao.getUserByEmail(email)
        if (userByEmail != null) {
            if (userByEmail.password == password) {
                Result.Success(email)
            } else {
                Result.Error("비밀번호를 확인해주세요.", ErrorCode.PASSWORD)
            }
        } else {
            Result.Error("존재하지 않는 이메일입니다.", ErrorCode.EMAIL)
        }
    } catch (e: Exception) {
        Result.Error(e.message)
    }

    override suspend fun getUserByEmail(email: String): Result<User> = try {
        val userEntity = userDao.getUserByEmail(email)
        if (userEntity != null) {
            Result.Success(userEntity.toUser())
        } else {
            Result.Error("유저를 찾을 수 없습니다.", ErrorCode.NOT_FOUND)
        }
    } catch (e: Exception) {
        Result.Error(e.message)
    }

    override suspend fun deleteUser(user: User): Result<Unit> = try {
        userDao.deleteUser(user.toUserEntity())
        Result.Success(Unit)
    } catch (e: Exception) {
        Result.Error(e.message)
    }
}
