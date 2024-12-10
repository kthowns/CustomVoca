package com.example.customvoca.repository

import com.example.customvoca.api.RetrofitInstance
import com.example.customvoca.dto.ApiError
import com.example.customvoca.dto.ApiResponse
import com.example.customvoca.dto.LoginDto
import org.json.JSONObject

class UserRepository {
    suspend fun login(request: LoginDto.Request): ApiResponse<*> {
        val response = RetrofitInstance.authApi.login(request)

        if(response.isSuccessful){
            return response.body() ?: throw Exception("Response Body is Null")
        }
        val apiError = parseApiError(response.errorBody()?.string())
        return ApiResponse<LoginDto.Response>(apiError.status, apiError.message, null)
    }

    private fun parseApiError(errorBody: String?): ApiError {
        // 예시로 errorBody를 파싱하는 로직을 추가
        // JSON 파싱을 통해 ApiError 객체로 변환
        val json = JSONObject(errorBody)
        return ApiError(
            status = json.optInt("status", -1),
            message = json.optString("message", "Unknown error")
        )
    }
}