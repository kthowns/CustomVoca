package com.example.customvoca.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.customvoca.database.Dic
import com.example.customvoca.database.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class VocaRepositoryImpl(application: Application) : VocaRepository {
    val db = Room.databaseBuilder(application,
        VocaDatabase::class.java, "voca_db")
        .build()
    override fun dicInsert(title: String){ //단어장
        db.dicListDao().insert(Dic(title))
    }
    override fun getWordAll() : List<Word>{
        return db.wordListDao().getAll()
    }
    override fun getLastWord(): Word {
        return db.wordListDao().getLastWord()
    }

    override fun insertWord(word: String, meaning: String, dic_id: Int) {
        db.wordListDao().insert(Word(word, meaning, dic_id))
        Log.d("Repository", "Insert "+db.wordListDao().getAll().toString())
    }
    override fun deleteWord(word: Word) {
        if(db.wordListDao().getWordCount() != 0) {
            db.wordListDao().delete(word)
            Log.d("Repository", "Deleted Successfully : " + word.toString())
        }else{
                Log.d("Repository", "Nothing to delete")
        }
    }
    override fun deleteWord(){
        if(db.wordListDao().getWordCount() != 0){
            val lastVoca = db.wordListDao().getLastWord()
            db.wordListDao().delete(lastVoca)
            Log.d("Repository", "Deleted Successfully : " + lastVoca.toString())
        }else{
            Log.d("Repository", "Nothing to delete")
        }
    }
}