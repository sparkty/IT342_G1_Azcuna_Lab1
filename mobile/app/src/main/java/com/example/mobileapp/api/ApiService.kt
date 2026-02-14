package com.example.mobileapp.network

import com.example.mobileapp.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface ApiService {

    @POST("/api/auth/register")
    suspend fun register(@Body user: User): Response<String>

    @POST("/api/auth/login")
    suspend fun login(@Body user: User): Response<String>

    @GET("/api/auth/me")
    suspend fun getUser(): Response<User>

    companion object {
        private const val BASE_URL = "http://10.0.2.2:8080" // Use 10.0.2.2 for Android emulator

        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}