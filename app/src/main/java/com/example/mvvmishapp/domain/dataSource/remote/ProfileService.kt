package com.example.mvvmishapp.domain.dataSource.remote

import com.example.mvvmishapp.domain.api.response.BaseResponse
import com.example.mvvmishapp.domain.api.response.UserProfileResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ProfileService {

    @Multipart
    @POST("user/profile?_method=PATCH")
    suspend fun createProfile(
        @Part name: MultipartBody.Part?,
        @Part birthDate: MultipartBody.Part?,
        @Part mainImage: MultipartBody.Part?
    ): Response<BaseResponse<UserProfileResponse>>

    @GET("user/profile")
    suspend fun getProfile(): Response<BaseResponse<UserProfileResponse>>
}