package com.example.mvvmishapp.data.di

import android.content.Context
import androidx.room.Room
import com.example.mvvmishapp.domain.dataSource.database.Database
import com.example.mvvmishapp.domain.dataSource.database.daos.UserProfileDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context): Database =
        Room
            .databaseBuilder(
                context,
                Database::class.java,
                "USER_DATABASE"
            )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideUserProfileDao(database: Database): UserProfileDao = database.getUserProfileDao()
}