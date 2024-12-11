package com.example.customvoca.api

import com.example.customvoca.dto.ApiResponse
import com.example.customvoca.dto.StatDto
import com.example.customvoca.dto.UpdateStat
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface StatApiService {
    // GET 요청
    @GET("/api/stats/all")
    suspend fun getStats(@Query("vocab_id") vocabId: Int): Response<ApiResponse<List<StatDto>>>

    @GET("/api/stats/detail")
    suspend fun getStatDetail(@Query("word_id") wordId: Int): Response<ApiResponse<StatDto>>

    @GET("/api/stats/lr")
    suspend fun getLearningRate(@Query("vocab_id") vocabId: Int): Response<ApiResponse<Double>>

    @GET("/api/stats/diff")
    suspend fun getDiff(@Query("word_id") wordId: Int): Response<ApiResponse<Double>>

    @PATCH("/api/stats/{wordId}")
    suspend fun editStat(@Path("wordId") wordId: Int,
                  @Body request: UpdateStat.Request): Response<ApiResponse<UpdateStat.Response>>
}
