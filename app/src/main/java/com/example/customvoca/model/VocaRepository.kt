package com.example.customvoca.model

import com.example.customvoca.database.Word

interface VocaRepository {
    suspend fun dicInsert(title: String)
    suspend fun getWordAll() : List<Word>
    suspend fun insertWord(word: String, meaning: String, dic_id: Int)
    suspend fun deleteWord(word: String)

}