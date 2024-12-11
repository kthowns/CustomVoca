package com.example.customvoca.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.customvoca.dto.CreateVocab
import com.example.customvoca.dto.VocabDto
import com.example.customvoca.repository.VocabRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VocabListViewModel(application: Application) : AndroidViewModel(application) {
    private val vocabRepository = VocabRepository()
    var vocabListItems = MutableLiveData<List<VocabDto>?>()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    var userId: Int? = 0

    fun setUId(userId: Int?){
        this.userId = userId
        Log.d("VocabListViewModel", "userID : " + this.userId)
        updateItems()
    }

    fun createVocab(title: String, description: String){
        viewModelScope.launch(Dispatchers.IO){
            withContext(Dispatchers.IO){
                val request = CreateVocab.Request(title, description)
                userId?.let { vocabRepository.createVocab(it, request) }
                delay(100)
            }
            updateItems()
        }
    }
    fun deleteVocab(vocabId: Int){
        viewModelScope.launch(Dispatchers.IO){
            withContext(Dispatchers.IO){
                vocabRepository.deleteVocab(vocabId)
            }
            updateItems()
        }
    }
    fun updateItems(){
        _isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {    // 데이터를 비동기적으로 가져옴
            val vocabData = userId?.let { vocabRepository.getVocabs(it).data }

            // UI 스레드에서 데이터를 업데이트
            withContext(Dispatchers.Main) {
                vocabListItems.value = vocabData // LiveData 업데이트
                _isLoading.postValue(false)// 로딩 종료
            }
        }
    }
}