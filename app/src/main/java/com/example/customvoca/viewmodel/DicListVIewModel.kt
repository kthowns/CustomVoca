package com.example.customvoca.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.customvoca.database.Dic
import com.example.customvoca.model.VocaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DicListViewModel(application: Application) : AndroidViewModel(application) {
    private val vocaRepository = VocaRepository.getInstance(application)
    var dicListItems = MutableLiveData<List<Dic>>()
    var dicName = ""
    init{
        updateItems()
    }
    fun onClickBtnAdd(){
        viewModelScope.launch(Dispatchers.IO){
            withContext(Dispatchers.IO){
                vocaRepository.insertDic(Dic(dicName))
            }
            updateItems()
        }
    }
    fun updateItems(){
        viewModelScope.launch(Dispatchers.IO) {
            dicListItems.postValue(vocaRepository.getDicAll())
        }
    }
    fun insertDic(dic: Dic){
        viewModelScope.launch(Dispatchers.IO){
            vocaRepository.insertDic(dic)
        }
    }
    fun deleteDic(dic: Dic){
        viewModelScope.launch(Dispatchers.IO){
            vocaRepository.deleteDic(dic)
        }
    }
}