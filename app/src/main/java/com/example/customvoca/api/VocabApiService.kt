package com.example.customvoca.api

import com.example.customvoca.dto.ApiResponse
import com.example.customvoca.dto.CreateVocab
import com.example.customvoca.dto.VocabDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface VocabApiService {
    // GET 요청
    @GET("/api/vocabs/all")
    suspend fun getVocabs(@Query("user_id") userId: Int): Response<ApiResponse<List<VocabDto>>>

    @GET("/api/vocabs/detail")
    suspend fun getVocabDetail(@Query("vocab_id") vocabId: Int): Response<ApiResponse<VocabDto>>

    @POST("/api/vocabs/{userId}")
    suspend fun createVocab(@Path("userId") userId: Int,
                    @Body request: CreateVocab.Request): Response<ApiResponse<CreateVocab.Response>>

    @PATCH("/api/vocabs/{vocabId}")
    suspend fun editVocab(@Path("vocabId") vocabId: Int,
                  @Body request: CreateVocab.Request): Response<ApiResponse<CreateVocab.Response>>

    @DELETE("/api/vocabs/{vocabId}")
    suspend fun deleteVocab(@Path("vocabId") vocabId: Int): Response<ApiResponse<VocabDto>>
}
