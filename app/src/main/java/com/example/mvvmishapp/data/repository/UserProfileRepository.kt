package com.example.mvvmishapp.data.repository

import com.example.mvvmishapp.domain.api.response.BaseResponse
import com.example.mvvmishapp.domain.api.response.UserProfileResponse
import com.example.mvvmishapp.domain.dataSource.database.daos.UserProfileDao
import com.example.mvvmishapp.domain.dataSource.remote.ProfileService
import com.example.mvvmishapp.domain.model.entities.UserProfileModel
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import retrofit2.Response

class UserProfileRepository(
    private val profileService: ProfileService,
    private val userProfileDao: UserProfileDao
) {

    suspend fun getUserProfile(): Response<BaseResponse<UserProfileResponse>> {
        return profileService.getProfile()
    }

    fun getLocalUserProfile(): Flow<List<UserProfileModel>> {
        return userProfileDao.getUserProfile()
    }

    suspend fun updateLocalUserProfile(userProfileModel: UserProfileModel) {
        return userProfileDao.saveProfile(userProfileModel)
    }

    suspend fun clear() {
        return userProfileDao.clear()
    }

    suspend fun createProfile(
        name: MultipartBody.Part? = null,
        mainImage: MultipartBody.Part? = null,
        birthDate: MultipartBody.Part? = null,
    ): Response<BaseResponse<UserProfileResponse>> {
        return profileService.createProfile(
            name = name,
            birthDate = birthDate,
            mainImage = mainImage
        )
    }
}