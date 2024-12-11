package com.example.customvoca.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.customvoca.api.RetrofitInstance
import com.example.customvoca.dto.ApiError
import com.example.customvoca.dto.ApiResponse
import com.example.customvoca.dto.CreateDef
import com.example.customvoca.dto.LoginDto
import com.example.customvoca.dto.DefDto
import com.example.customvoca.dto.VocabDto
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DefRepository {
    suspend fun getDefs(wordId: Int): ApiResponse<List<DefDto>> {
        val response = RetrofitInstance.defApi.getDefs(wordId)

        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Response Body is Null")
        }
        val apiError = parseApiError(response.errorBody()?.string())
        return ApiResponse(apiError.status, apiError.message, null)
    }

    suspend fun getDefDetail(defId: Int): ApiResponse<DefDto> {
        val response = RetrofitInstance.defApi.getDefDetail(defId)

        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Response Body is Null")
        }
        val apiError = parseApiError(response.errorBody()?.string())
        return ApiResponse(apiError.status, apiError.message, null)
    }

    suspend fun createDef(
        wordId: Int,
        request: CreateDef.Request
    ): ApiResponse<CreateDef.Response> {
        val response = RetrofitInstance.defApi.createDef(wordId, request)

        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Response Body is Null")
        }
        val apiError = parseApiError(response.errorBody()?.string())
        return ApiResponse(apiError.status, apiError.message, null)
    }

    suspend fun editDef(defId: Int, request: CreateDef.Request): ApiResponse<CreateDef.Response> {
        val response = RetrofitInstance.defApi.editDef(defId, request)

        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Response Body is Null")
        }
        val apiError = parseApiError(response.errorBody()?.string())
        return ApiResponse(apiError.status, apiError.message, null)
    }

    suspend fun deleteDef(defId: Int): ApiResponse<DefDto> {
        val response = RetrofitInstance.defApi.deleteDef(defId)

        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Response Body is Null")
        }
        val apiError = parseApiError(response.errorBody()?.string())
        return ApiResponse(apiError.status, apiError.message, null)
    }

    private fun parseApiError(errorBody: String?): ApiError {
        // 예시로 errorBody를 파싱하는 로직을 추가
        // JSON 파싱을 통해 ApiError 객체로 변환
        val json = errorBody?.let { JSONObject(it) }
        if (json != null) {
            return ApiError(
                status = json.optInt("status", -1),
                message = json.optString("message", "Unknown error")
            )
        }
        return ApiError(
            status = 500,
            message = "Internal Server Error"
        )
    }
}