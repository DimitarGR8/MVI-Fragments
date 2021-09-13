package com.example.mvvmishapp.data.di

import com.example.mvvmishapp.data.repository.UserProfileRepository
import com.example.mvvmishapp.domain.dataSource.database.daos.UserProfileDao
import com.example.mvvmishapp.domain.dataSource.preferences.UserPreferences
import com.example.mvvmishapp.domain.dataSource.remote.AuthenticationService
import com.example.mvvmishapp.domain.dataSource.remote.ProfileService
import com.example.mvvmishapp.domain.repository.AuthenticationRepository
import com.example.mvvmishapp.domain.repository.CredentialsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideProfileRepository(
        profileService: ProfileService,
        userProfileDao: UserProfileDao
    ): UserProfileRepository = UserProfileRepository(profileService, userProfileDao)

    @Provides
    @Singleton
    fun provideAuthRepository(
        authService: AuthenticationService
    ): AuthenticationRepository = AuthenticationRepository(authService)

    @Provides
    @Singleton
    fun provideUserDataRepository(
        userPreferences: UserPreferences
    ): CredentialsRepository = CredentialsRepository(userPreferences)
}