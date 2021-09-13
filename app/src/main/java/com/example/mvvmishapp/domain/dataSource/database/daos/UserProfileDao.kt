package com.example.mvvmishapp.domain.dataSource.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mvvmishapp.domain.model.entities.UserProfileModel
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProfile(profile: UserProfileModel)

    @Query("SELECT * from user_profile")
    fun getUserProfile(): Flow<List<UserProfileModel>>

    @Query("DELETE from user_profile")
    suspend fun clear()
}