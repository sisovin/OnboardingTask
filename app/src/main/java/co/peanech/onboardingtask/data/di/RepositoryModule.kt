package co.peanech.onboardingtask.data.di

import co.peanech.onboardingtask.data.dao.UserDao
import co.peanech.onboardingtask.data.repository.UserRepositoryImpl
import co.peanech.onboardingtask.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepositoryImpl(userDao)
    }
}
