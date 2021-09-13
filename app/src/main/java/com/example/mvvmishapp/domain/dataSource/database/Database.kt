package com.example.mvvmishapp.domain.dataSource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mvvmishapp.domain.dataSource.database.daos.UserProfileDao
import com.example.mvvmishapp.domain.dataSource.database.typeConvertes.ImageListConverter
import com.example.mvvmishapp.domain.model.entities.UserProfileModel

@Database(
    entities = [
        UserProfileModel::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    ImageListConverter::class
)

abstract class Database : RoomDatabase() {
    abstract fun getUserProfileDao(): UserProfileDao
}