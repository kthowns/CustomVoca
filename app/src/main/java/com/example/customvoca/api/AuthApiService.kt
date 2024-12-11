package com.example.customvoca.api

import com.example.customvoca.dto.ApiResponse
import com.example.customvoca.dto.LoginDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("/api/login")
    suspend fun login(@Body loginDto: LoginDto.Request): Response<ApiResponse<LoginDto.Response>>
}