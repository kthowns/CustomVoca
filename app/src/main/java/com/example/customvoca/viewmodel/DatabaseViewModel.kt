package com.example.customvoca.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.customvoca.database.Voca
import com.example.customvoca.database.VocaDatabase
import com.example.customvoca.database.VocaList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DatabaseViewModel(application: Application) : AndroidViewModel(application) {
    private val db = Room.databaseBuilder(application,
        VocaDatabase::class.java, "voca_db")
        .build()
    var word = ""
    var meaning = ""
    var txtResult = MutableLiveData<String>()
    init{
        updateListVoca()
    }
    fun vocaListInsert(title: String){ //단어장 추가
        viewModelScope.launch(Dispatchers.IO) {
            db.vocaListDao().insert(VocaList(title))
        }
    }
    fun getVocaAll() : List<Voca>{
        return db.vocaDao().getAll()
    }
    fun btnSaveClicked(){
        viewModelScope.launch(Dispatchers.IO){
            withContext(Dispatchers.IO){
                db.vocaDao().insert(Voca(word, meaning, 0))
            }
            updateListVoca()
        }
    }
    fun btnDeleteClicked(){
        viewModelScope.launch(Dispatchers.IO){
            withContext(Dispatchers.IO){
                if(db.vocaDao().getVocaCount() != 0){
                    val lastVoca = db.vocaDao().getLastVoca()
                    db.vocaDao().delete(lastVoca)
                    Log.d("Voca", "Deleted Successfully : " + lastVoca.toString())
                }else{
                    Log.d("Voca", "Nothing to delete")
                }
            }
            updateListVoca()
        }
    }
    fun updateListVoca(){
        viewModelScope.launch(Dispatchers.IO) {
            var vocaList = getVocaAll()
            var txt = ""
            vocaList.forEach{
                txt = txt +it.word + " / " + it.meaning + "\n"
            }
            txtResult.postValue(txt)
        }
    }
}