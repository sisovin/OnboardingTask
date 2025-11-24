package co.peanech.onboardingtask.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = false) val email: String,
    val password: String,
    val username: String,
)
