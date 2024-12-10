package com.example.customvoca.api

import com.example.customvoca.dto.ApiResponse
import com.example.customvoca.dto.CreateDef
import com.example.customvoca.dto.DefDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface DefApiService {
    // GET 요청
    @GET("/api/defs/all")
    suspend fun getDefs(@Query("word_id") wordId: Int): Response<ApiResponse<List<DefDto>>>

    @GET("/api/defs/detail")
    suspend fun getDefDetail(@Query("def_id") defId: Int): Response<ApiResponse<DefDto>>

    @POST("/api/defs/{defId}")
    suspend fun createDef(
        @Path("defId") defId: Int,
        @Body request: CreateDef.Request
    ): Response<ApiResponse<CreateDef.Response>>

    @PATCH("/api/defs/{defId}")
    suspend fun editDef(
        @Path("defId") wordId: Int,
        @Body request: CreateDef.Request
    ): Response<ApiResponse<CreateDef.Response>>

    @DELETE("/api/defs/{defId}")
    suspend fun deleteDef(@Path("defId") wordId: Int): Response<ApiResponse<DefDto>>
}