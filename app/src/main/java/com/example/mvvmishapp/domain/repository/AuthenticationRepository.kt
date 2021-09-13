package com.example.mvvmishapp.domain.repository

import com.example.mvvmishapp.domain.api.request.AuthRequest
import com.example.mvvmishapp.domain.api.response.AuthResponse
import com.example.mvvmishapp.domain.api.response.BaseResponse
import com.example.mvvmishapp.domain.dataSource.remote.AuthenticationService
import retrofit2.Response

class AuthenticationRepository(private val authService: AuthenticationService) {

    suspend fun login(request: AuthRequest): Response<BaseResponse<AuthResponse>> {
        return authService.login(request)
    }

    suspend fun logout(): Response<Void> {
        return authService.logout()
    }
}