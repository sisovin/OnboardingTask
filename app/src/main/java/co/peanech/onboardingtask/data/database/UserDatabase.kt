package co.peanech.onboardingtask.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import co.peanech.onboardingtask.data.dao.UserDao
import co.peanech.onboardingtask.data.model.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
