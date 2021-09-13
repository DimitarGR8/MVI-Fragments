package com.example.mvvmishapp.data.di

import android.content.Context
import com.example.mvvmishapp.R
import com.example.mvvmishapp.common.Constants.API_PATH
import com.example.mvvmishapp.domain.dataSource.remote.AuthenticationInterceptor
import com.example.mvvmishapp.domain.dataSource.remote.AuthenticationService
import com.example.mvvmishapp.domain.dataSource.remote.ProfileService
import com.example.mvvmishapp.domain.repository.CredentialsRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    @ExperimentalSerializationApi
    fun provideSerializer(): Converter.Factory = Json(builderAction = {
        ignoreUnknownKeys = true
        isLenient = true
    }).asConverterFactory("application/json".toMediaType())


    @Provides
    @Singleton
    fun provideClient(
        @Named("logging") logger: Interceptor,
        @Named("authentication") authenticator: Interceptor
    ): OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(logger)
        .addInterceptor(authenticator)
        .build()

    @Provides
    @Singleton
    fun getBaseUrl(@ApplicationContext context: Context): String =
        context.getString(R.string.endpoint)

    @Provides
    @Singleton
    @Named("logging")
    fun getLogger(): Interceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    @Named("authentication")
    fun getAuthenticator(credentialsRepository: CredentialsRepository): Interceptor =
        AuthenticationInterceptor(credentialsRepository)


    @Provides
    @Singleton
    fun provideRetrofitInstance(
        converter: Converter.Factory,
        client: OkHttpClient,
        apiAddress: String
    ): Retrofit = Retrofit
        .Builder()
        .baseUrl(apiAddress + API_PATH)
        .client(client)
        .addConverterFactory(converter)
        .build()

    @Provides
    @Singleton
    fun provideAuthService(
        retrofit: Retrofit
    ): AuthenticationService = retrofit.create(AuthenticationService::class.java)

    @Provides
    @Singleton
    fun provideProfileService(
        retrofit: Retrofit
    ): ProfileService = retrofit.create(ProfileService::class.java)

}