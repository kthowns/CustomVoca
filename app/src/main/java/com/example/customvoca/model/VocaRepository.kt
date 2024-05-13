package com.example.customvoca.model

import androidx.lifecycle.LiveData
import com.example.customvoca.database.Word

interface VocaRepository {
    fun dicInsert(title: String)
    fun getWordAll() : List<Word>
    fun insertWord(word: String, meaning: String, dic_id: Int)
    fun deleteWord(word: Word)
    fun deleteWord()
    fun getLastWord() : Word
}