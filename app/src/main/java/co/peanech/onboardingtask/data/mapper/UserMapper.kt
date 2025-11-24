package co.peanech.onboardingtask.data.mapper

import co.peanech.onboardingtask.data.model.UserEntity
import co.peanech.onboardingtask.domain.model.User

fun UserEntity.toUser(): User {
    return User(
        email = email, password = password, username = username
    )
}

fun User.toUserEntity(): UserEntity {
    return UserEntity(
        email = email, password = password, username = username
    )
}
