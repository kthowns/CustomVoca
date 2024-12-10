package com.example.customvoca.api

import com.example.customvoca.dto.ApiResponse
import com.example.customvoca.dto.CreateWord
import com.example.customvoca.dto.WordDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface WordApiService {
    // GET 요청
    @GET("/api/words/all")
    suspend fun getWords(@Query("user_id") userId: Int): Response<ApiResponse<List<WordDto>>>

    @GET("/api/words/detail")
    suspend fun getWordDetail(@Query("word_id") wordId: Int): Response<ApiResponse<WordDto>>

    @POST("/api/words/{wordId}")
    suspend fun createWord(@Path("wordId") wordId: Int,
                    @Body request: CreateWord.Request): Response<ApiResponse<CreateWord.Response>>

    @PATCH("/api/words/{wordId}")
    suspend fun editWord(@Path("wordId") wordId: Int,
                  @Body request: CreateWord.Request): Response<ApiResponse<CreateWord.Response>>

    @DELETE("/api/words/{wordId}")
    suspend fun deleteWord(@Path("wordId") wordId: Int): Response<ApiResponse<WordDto>>
}
