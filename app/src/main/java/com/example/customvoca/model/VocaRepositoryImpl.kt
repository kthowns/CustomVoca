package com.example.customvoca.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.customvoca.database.Dic
import com.example.customvoca.database.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VocaRepositoryImpl(application: Application) : ViewModel(), VocaRepository {
    private val db = Room.databaseBuilder(application,
        VocaDatabase::class.java, "voca_db")
        .build()
    override suspend fun dicInsert(title: String){ //단어장 추가
        withContext(Dispatchers.IO) {
            db.dicListDao().insert(Dic(title))
        }
    }
    override suspend fun getWordAll() : List<Word>{
        return db.wordListDao().getAll()
    }

    override suspend fun insertWord(word: String, meaning: String, dic_id: Int) {
        withContext(Dispatchers.IO){
            db.wordListDao().insert(Word(word, meaning, dic_id))
        }
    }
    override suspend fun deleteWord(word: String) {
        withContext(Dispatchers.IO){
            if(db.wordListDao().getWordCount() != 0){
                val lastVoca = db.wordListDao().getLastWord()
                db.wordListDao().delete(lastVoca)
                Log.d("Voca", "Deleted Successfully : " + lastVoca.toString())
            }else{
                Log.d("Voca", "Nothing to delete")
            }
        }
    }
}