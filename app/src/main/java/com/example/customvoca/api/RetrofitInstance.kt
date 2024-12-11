package com.example.customvoca.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:8080"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    // Vocab API 서비스
    val vocabApi: VocabApiService by lazy {
        retrofit.create(VocabApiService::class.java)
    }

    // Word API 서비스
    val wordApi: WordApiService by lazy {
        retrofit.create(WordApiService::class.java)
    }

    // User API 서비스
    val authApi: AuthApiService by lazy {
        retrofit.create(AuthApiService::class.java)
    }

    val defApi: DefApiService by lazy {
        retrofit.create(DefApiService::class.java)
    }

    val statApi: StatApiService by lazy {
        retrofit.create(StatApiService::class.java)
    }
}
