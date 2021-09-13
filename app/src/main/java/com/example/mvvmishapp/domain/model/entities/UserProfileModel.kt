package com.example.mvvmishapp.domain.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
class UserProfileModel(
    @PrimaryKey
    val id: Int,
    val firstname: String?,
    val mainImage: String?,
    val birthDate: Long?,
)