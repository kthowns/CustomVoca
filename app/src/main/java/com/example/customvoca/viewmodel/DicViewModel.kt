package com.example.customvoca.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.customvoca.database.Word
import com.example.customvoca.model.RecyclerViewAdapter
import com.example.customvoca.model.VocaRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DicViewModel(application: Application) : AndroidViewModel(application) {
    private var vocaRepository = VocaRepositoryImpl(application)
    var recyclerItems = MutableLiveData<List<Word>>()
    var word = ""
    var meaning = ""
    init {
        viewModelScope.launch(Dispatchers.IO) {
            recyclerItems.postValue(vocaRepository.getWordAll())
        }
    }
    fun btnAddClicked(){
        Log.d("dicViewModel", "Add Button Clicked")
        viewModelScope.launch(Dispatchers.IO){
            withContext(Dispatchers.IO){
                vocaRepository.insertWord(word, meaning, 0)
            }
            recyclerItems.postValue(vocaRepository.getWordAll())
        }
    }
    fun btnDeleteClicked(){
        Log.d("dicViewModel", "Delete Button Clicked")
        viewModelScope.launch(Dispatchers.IO){
            withContext(Dispatchers.IO){
                vocaRepository.deleteWord()
            }
            recyclerItems.postValue(vocaRepository.getWordAll())
        }
    }
}