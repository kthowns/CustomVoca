package com.example.customvoca.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.customvoca.database.Dic
import com.example.customvoca.database.Word
import com.example.customvoca.model.VocaDatabase
import com.example.customvoca.model.VocaRepository
import com.example.customvoca.model.VocaRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DatabaseViewModel(application: Application) : AndroidViewModel(application) {
    private val vocaRepository = VocaRepositoryImpl(application)
    var word = ""
    var meaning = ""
    var txtResult = MutableLiveData<String>()
    init{
        updateListVoca()
    }
    fun btnSaveClicked(){
        viewModelScope.launch(Dispatchers.IO){
            vocaRepository.insertWord(word, meaning, 0)
            updateListVoca()
        }
    }
    fun btnDeleteClicked(){
        viewModelScope.launch(Dispatchers.IO){
            vocaRepository.deleteWord(word)
            updateListVoca()
        }
    }
    fun updateListVoca(){
        viewModelScope.launch(Dispatchers.IO) {
            var vocaList = vocaRepository.getWordAll()
            var txt = ""
            vocaList.forEach{
                txt = txt +it.word + " / " + it.meaning + "\n"
            }
            txtResult.postValue(txt)
        }
    }
}