package com.example.customvoca.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.customvoca.database.Dic
import com.example.customvoca.model.VocaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DicListViewModel(application: Application) : AndroidViewModel(application) {
    private val vocaRepository = VocaRepository.getInstance(application)
    var dicListItems = MutableLiveData<List<Dic>>()

    init{
        updateItems()
    }

    fun addDic(dicName: String){
        viewModelScope.launch(Dispatchers.IO){
            withContext(Dispatchers.IO){
                vocaRepository.insertDic(Dic(dicName))
                delay(100)
            }
            updateItems()
        }
    }
    fun deleteDic(dic: Dic){
        viewModelScope.launch(Dispatchers.IO){
            withContext(Dispatchers.IO){
                vocaRepository.deleteDic(dic)
                delay(100)
            }
            updateItems()
        }
    }
    fun updateItems(){
        viewModelScope.launch(Dispatchers.IO) {
            dicListItems.postValue(vocaRepository.getDicAll())
        }
    }
}