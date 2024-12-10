package com.example.customvoca.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.customvoca.api.RetrofitInstance
import com.example.customvoca.dto.ApiError
import com.example.customvoca.dto.ApiResponse
import com.example.customvoca.dto.CreateWord
import com.example.customvoca.dto.LoginDto
import com.example.customvoca.dto.VocabDto
import com.example.customvoca.dto.WordDto
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WordRepository {
    suspend fun getWords(vocabId: Int): ApiResponse<List<WordDto>> {
        val response = RetrofitInstance.wordApi.getWords(vocabId)

        if(response.isSuccessful){
            return response.body() ?: throw Exception("Response Body is Null")
        }
        val apiError = parseApiError(response.errorBody()?.string())
        return ApiResponse(apiError.status, apiError.message, null)
    }

    suspend fun getWordDetail(wordId: Int): ApiResponse<WordDto>{
        val response = RetrofitInstance.wordApi.getWordDetail(wordId)

        if(response.isSuccessful){
            return response.body() ?: throw Exception("Response Body is Null")
        }
        val apiError = parseApiError(response.errorBody()?.string())
        return ApiResponse(apiError.status, apiError.message, null)
    }

    suspend fun createWord(vocabId: Int, request: CreateWord.Request): ApiResponse<CreateWord.Response>{
        val response = RetrofitInstance.wordApi.createWord(vocabId, request)

        if(response.isSuccessful){
            return response.body() ?: throw Exception("Response Body is Null")
        }
        val apiError = parseApiError(response.errorBody()?.string())
        return ApiResponse(apiError.status, apiError.message, null)
    }

    suspend fun editWord(wordId: Int, request: CreateWord.Request): ApiResponse<CreateWord.Response>{
        val response = RetrofitInstance.wordApi.editWord(wordId, request)

        if(response.isSuccessful){
            return response.body() ?: throw Exception("Response Body is Null")
        }
        val apiError = parseApiError(response.errorBody()?.string())
        return ApiResponse(apiError.status, apiError.message, null)
    }

    suspend fun deleteWord(wordId: Int): ApiResponse<WordDto>{
        val response = RetrofitInstance.wordApi.deleteWord(wordId)

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