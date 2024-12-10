package com.example.customvoca.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.customvoca.api.RetrofitInstance
import com.example.customvoca.dto.ApiError
import com.example.customvoca.dto.ApiResponse
import com.example.customvoca.dto.CreateVocab
import com.example.customvoca.dto.LoginDto
import com.example.customvoca.dto.VocabDto
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VocabRepository {
    suspend fun getVocabs(userId: Int): ApiResponse<List<VocabDto>> {
        val response = RetrofitInstance.vocabApi.getVocabs(userId)

        if(response.isSuccessful){
            return response.body() ?: throw Exception("Response Body is Null")
        }
        val apiError = parseApiError(response.errorBody()?.string())
        return ApiResponse(apiError.status, apiError.message, null)
    }

    suspend fun getVocabDetail(vocabId: Int): ApiResponse<VocabDto>{
        val response = RetrofitInstance.vocabApi.getVocabDetail(vocabId)

        if(response.isSuccessful){
            return response.body() ?: throw Exception("Response Body is Null")
        }
        val apiError = parseApiError(response.errorBody()?.string())
        return ApiResponse(apiError.status, apiError.message, null)
    }

    suspend fun createVocab(userId: Int, request: CreateVocab.Request): ApiResponse<CreateVocab.Response>{
        val response = RetrofitInstance.vocabApi.createVocab(userId, request)

        if(response.isSuccessful){
            return response.body() ?: throw Exception("Response Body is Null")
        }
        val apiError = parseApiError(response.errorBody()?.string())
        return ApiResponse(apiError.status, apiError.message, null)
    }

    suspend fun editVocab(vocabId: Int, request: CreateVocab.Request): ApiResponse<CreateVocab.Response>{
        val response = RetrofitInstance.vocabApi.editVocab(vocabId, request)

        if(response.isSuccessful){
            return response.body() ?: throw Exception("Response Body is Null")
        }
        val apiError = parseApiError(response.errorBody()?.string())
        return ApiResponse(apiError.status, apiError.message, null)
    }

    suspend fun deleteVocab(vocabId: Int): ApiResponse<VocabDto>{
        val response = RetrofitInstance.vocabApi.deleteVocab(vocabId)

        if(response.isSuccessful){
            return response.body() ?: throw Exception("Response Body is Null")
        }
        val apiError = parseApiError(response.errorBody()?.string())
        return ApiResponse(apiError.status, apiError.message, null)
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