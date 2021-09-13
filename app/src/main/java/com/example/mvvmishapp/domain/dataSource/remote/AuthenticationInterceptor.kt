package com.example.mvvmishapp.domain.dataSource.remote

import com.example.mvvmishapp.domain.repository.CredentialsRepository
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthenticationInterceptor @Inject constructor(
    private val credentialsRepository: CredentialsRepository) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val authToken = credentialsRepository.getToken()
        return chain.proceed(
            chain.request()
                .newBuilder()
                .apply {
                    if (authToken.isNotEmpty()) {
                        addHeader("Authorization", "Bearer $authToken")
                    }
                    addHeader("Accept","application/json")
                }.build()
        )
    }
}