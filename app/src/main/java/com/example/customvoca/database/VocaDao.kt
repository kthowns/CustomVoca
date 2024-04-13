package com.example.customvoca.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface VocaDao{
    /*
        Voca(vocas) Dao
    */
    @Query("SELECT * FROM vocas;")
    fun getAll() : List<Voca>
    @Query("SELECT COUNT(*) FROM vocas;")
    fun getVocaCount() : Int
    @Query("SELECT * FROM vocas WHERE tableId = :tableId AND word = :word;")
    fun getMeaning(tableId: Int, word: String) : Voca
    @Query("SELECT * FROM vocas ORDER BY vocaId DESC LIMIT 1;")
    fun getLastVoca() : Voca
    @Query("SELECT * FROM vocas WHERE vocaId = :vocaId;")
    fun getVocaById(vocaId: Int) : Voca
    @Query("SELECT * FROM vocas WHERE word=:word;")
    fun getVocaByWord(word: String) : Voca
    @Insert
    fun insert(voca: Voca)
    @Update
    fun update(voca: Voca)
    @Delete
    fun delete(voca: Voca)
}

@Dao
interface VocaListDao{
    /*
        VocaList(voca_list) Dao
    */
    @Query("SELECT * FROM voca_list;")
    suspend fun getAll() : List<VocaList>
    @Insert
    suspend fun insert(vocaList: VocaList)
    @Update
    suspend fun update(vocaList: VocaList)
    @Delete
    suspend fun delete(vocaList: VocaList)
}