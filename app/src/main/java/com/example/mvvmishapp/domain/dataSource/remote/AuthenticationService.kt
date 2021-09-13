package com.example.mvvmishapp.domain.dataSource.remote

import com.example.mvvmishapp.domain.api.request.AuthRequest
import com.example.mvvmishapp.domain.api.response.AuthResponse
import com.example.mvvmishapp.domain.api.response.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthenticationService {
    @POST("login")
    @Headers("Content-Type: application/json")
    suspend fun login(@Body request: AuthRequest): Response<BaseResponse<AuthResponse>>

    @POST("logout")
    @Headers("Content-Type: application/json")
    suspend fun logout(): Response<Void>
}