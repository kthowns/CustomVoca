package com.example.customvoca.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.customvoca.database.Dic
import com.example.customvoca.database.Word
import com.example.customvoca.model.VocaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DicViewModel(application: Application) : AndroidViewModel(application) {
    private val vocaRepository = VocaRepository.getInstance(application)
    private var _currentDic = MutableLiveData<Dic>()
    val currentDic : LiveData<Dic>
        get() = _currentDic
    var dicItems = MutableLiveData<List<Word>>()

    fun addWord(word: String, meaning: String){
        viewModelScope.launch(Dispatchers.IO){
            withContext(Dispatchers.IO){
                vocaRepository.insertWord(word, meaning, getCurrentDicId())
                delay(100)
            }
            updateItems()
        }
    }
    fun deleteWord(word: Word){
        Log.d("dicViewModel", "Delete Button Clicked")
        viewModelScope.launch(Dispatchers.IO){
            withContext(Dispatchers.IO){
                vocaRepository.deleteWord(word)
                delay(100)
            }
            updateItems()
        }
    }
    fun setCurrentDic(dicId: Int){
        viewModelScope.launch(Dispatchers.IO){
            _currentDic.postValue(vocaRepository.getDicById(dicId))
            Log.d("DicViewModel", "Setting CurrentDic")
        }
    }
    fun getCurrentDicId() : Int{
        val dic = _currentDic.value
        if(dic != null)
            return dic.dic_id
        return 0
    }
    fun updateItems(){
        viewModelScope.launch(Dispatchers.IO) {
            dicItems.postValue(vocaRepository.getWordByDic(getCurrentDicId()))
        }
    }
}