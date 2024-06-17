package com.example.customvoca.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.customvoca.database.Word
import com.example.customvoca.model.VocaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DicViewModel(application: Application) : AndroidViewModel(application) {
    private val vocaRepository = VocaRepository.getInstance(application)
    var dicItems = MutableLiveData<List<Word>>()
    var word = ""
    var meaning = ""
    var currentDic = 0
    init {
        updateItems()
    }
    fun btnAddClicked(){
        Log.d("dicViewModel", "Add Button Clicked")
        viewModelScope.launch(Dispatchers.IO){
            withContext(Dispatchers.IO){
                vocaRepository.insertWord(word, meaning, currentDic)
            }
            updateItems()
        }
    }
    fun deleteWord(word: Word){
        Log.d("dicViewModel", "Delete Button Clicked")
        viewModelScope.launch(Dispatchers.IO){
            withContext(Dispatchers.IO){
                vocaRepository.deleteWord(word)
            }
            dicItems.postValue(vocaRepository.getWordAll())
        }
    }
    fun updateItems(){
        viewModelScope.launch(Dispatchers.IO) {
            dicItems.postValue(vocaRepository.getWordByDic(currentDic))
        }
    }
}