package com.example.customvoca.model

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.customvoca.database.Dic
import com.example.customvoca.database.Word

class VocaRepository private constructor(private val application: Application) {
    private val db = VocaDatabase.getInstance(application)
    companion object {
        @Volatile
        private var INSTANCE: VocaRepository? = null
        fun getInstance(application: Application): VocaRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = VocaRepository(application)
                INSTANCE = instance
                Log.d("Repository", "Instance Created : " + INSTANCE.toString())
                instance
            }
        }
    }
    fun getWordAll() : List<Word>{
        return db.wordListDao().getAll()
    }
    fun getWordByDic(num: Int) : List<Word>{
        return db.wordListDao().getWordByDic(num)
    }
    fun getDicAll() : List<Dic>{
        return db.dicListDao().getAll()
    }
    fun getLastWord(): Word {
        return db.wordListDao().getLastWord()
    }
    fun getDicById(dicId: Int): Dic {
        return db.dicListDao().getDic(dicId)
    }
    fun insertWord(word: Word) {
        db.wordListDao().insert(word)
        Log.d("Repository", "Insert "+db.wordListDao().getAll().toString())
    }
    fun insertWord(word: String, meaning: String, dic_id: Int) {
        db.wordListDao().insert(Word(word, meaning, dic_id))
        Log.d("Repository", "Insert "+db.wordListDao().getAll().toString())
    }
    fun insertDic(dic: Dic){
        db.dicListDao().insert(dic)
        Log.d("Repository", "Insert"+db.dicListDao().getAll().toString())
    }
    fun insertDic(name: String){
        db.dicListDao().insert(Dic(name))
        Log.d("Repository", "Insert"+db.dicListDao().getAll().toString())
    }
    fun deleteWord(word: Word) {
        if(db.wordListDao().getWordCount() != 0) {
            db.wordListDao().delete(word)
            Log.d("Repository", "Deleted Successfully : " + word.toString())
        }else{
            Log.d("Repository", "Nothing to delete")
        }
    }
    fun deleteDic(dic: Dic){
        if(db.dicListDao().getDicCount() != 0) {
            db.dicListDao().delete(dic)
            Log.d("Repository", "Deleted Successfully : " + dic.toString())
            db.wordListDao().dropByListNum(dic.dic_id)
        }else{
            Log.d("Repository", "Nothing to delete")
        }
    }
    fun deleteDic(dic_id: Int){
        if(db.dicListDao().getDicCount() != 0) {
            db.dicListDao().dropList(dic_id)
            Log.d("Repository", "Deleted Successfully : " + dic_id.toString())
            db.wordListDao().dropByListNum(dic_id)
        }else{
            Log.d("Repository", "Nothing to delete")
        }
    }
    fun deleteWord(num: Int){
        if(db.wordListDao().getWordCount() != 0){
            db.wordListDao().delete(num)
            Log.d("Repository", "Deleted Successfully : " + num.toString())
        }else{
            Log.d("Repository", "Nothing to delete")
        }
    }
}