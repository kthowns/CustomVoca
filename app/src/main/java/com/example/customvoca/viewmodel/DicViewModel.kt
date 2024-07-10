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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DicViewModel(application: Application) : AndroidViewModel(application) {
    private val vocaRepository = VocaRepository.getInstance(application)
    private var _currentDic = MutableLiveData<Dic>()
    val currentDic : LiveData<Dic>
        get() = _currentDic
    var dicItems = MutableLiveData<List<Word>>()
    var word = ""
    var meaning = ""
    fun setCurrentDic(dicId: Int){
        viewModelScope.launch(Dispatchers.IO){
            _currentDic.postValue(vocaRepository.getDicById(dicId))
            Log.d("DicViewModel", "Setting CurrentDic")
        }
    }
    fun btnAddClicked(){
        Log.d("dicViewModel", "Add Button Clicked")
        viewModelScope.launch(Dispatchers.IO){
            withContext(Dispatchers.IO){
                val dic = _currentDic.value
                if(dic != null)
                    vocaRepository.insertWord(word, meaning, dic.dic_id)
                else
                    Log.d("dicViewModel", "btnAddClicked() Failed")
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
            val dic = _currentDic.value
            if(dic != null)
                dicItems.postValue(vocaRepository.getWordByDic(dic.dic_id))
            else
                Log.d("dicViewModel", "updateItems() Failed")
        }
    }
    fun getCurrentDicTitle() : String {
        val dic = _currentDic.value
        if(dic != null)
            return dic.name
        return ""
    }
    fun getCurrentDicId() : Int{
        val dic = _currentDic.value
        if(dic != null)
            return dic.dic_id
        return 0
    }
}