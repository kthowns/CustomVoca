package com.example.customvoca.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WordListDao{
    /*
        WordList(word_list) Dao
    */
    @Query("SELECT * FROM word_list;")
    fun getAll() : List<Word>
    @Query("SELECT COUNT(*) FROM word_list;")
    fun getWordCount() : Int
    @Query("SELECT * FROM word_list ORDER BY word_id DESC LIMIT 1;")
    fun getLastWord() : Word
    @Query("SELECT * FROM word_list WHERE word=:word;")
    fun getWord(word: String) : Word
    @Insert
    fun insert(word: Word)
    @Update
    fun update(word: Word)
    @Delete
    fun delete(word: Word)
}

@Dao
interface DicListDao{
    /*
        DicList(dic_list) Dao
    */
    @Query("SELECT * FROM dic_list;")
    suspend fun getAll() : List<Dic>
    @Insert
    suspend fun insert(dic: Dic)
    @Update
    suspend fun update(dic: Dic)
    @Delete
    suspend fun delete(dic: Dic)
}