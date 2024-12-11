package com.example.customvoca.viewmodel

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.customvoca.dto.CreateDef
import com.example.customvoca.dto.CreateVocab
import com.example.customvoca.dto.CreateWord
import com.example.customvoca.dto.WordWithDef
import com.example.customvoca.repository.DefRepository
import com.example.customvoca.repository.VocabRepository
import com.example.customvoca.repository.WordRepository
import com.example.customvoca.type.POS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WordListViewModel(
    private val wordRepository: WordRepository,
    private val defRepository: DefRepository,
    private val vocabRepository: VocabRepository
) : ViewModel() {

    private val _wordWithDefs = MutableLiveData<List<WordWithDef>>()
    val wordWithDefs: LiveData<List<WordWithDef>> get() = _wordWithDefs
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    var currentDicTitle = MutableLiveData("")
    var currentVocabId = 0

    fun loadWordsWithDefs() {
        _isLoading.postValue(true)
        viewModelScope.launch {
            currentDicTitle.postValue(vocabRepository.getVocabDetail(currentVocabId).data?.title)
            val wordResponse = wordRepository.getWords(currentVocabId)
            val words = wordResponse.data ?: emptyList()

            val wordWithDefsList = mutableListOf<WordWithDef>()
            Log.d("LoadWordsWIthDefsList", wordWithDefsList.toString())
            for (word in words) {
                val defResponse = defRepository.getDefs(word.wordId)
                val defs = defResponse.data ?: emptyList()
                if (defs.isNotEmpty()) {
                    // 첫 번째 정의만 사용
                    val def = defs.first()
                    wordWithDefsList.add(
                        WordWithDef(
                            wordId = word.wordId,
                            expression = word.expression,
                            definition = def.definition,
                            type = def.type.type
                        )
                    )
                } else {
                    // 정의가 없을 경우에 대한 처리 (옵션)
                    Log.d("WordListViewModel", "No definition found for word: ${word.expression}")
                }
            }
            _wordWithDefs.postValue(wordWithDefsList)
            _isLoading.postValue(false)
        }
    }

    class Factory(
        private val wordRepository: WordRepository,
        private val defRepository: DefRepository,
        private val vocabRepository: VocabRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WordListViewModel::class.java)) {
                return WordListViewModel(wordRepository, defRepository, vocabRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
    fun createWord(expression: String, definition: String, type: POS){
        viewModelScope.launch(Dispatchers.IO){
            val responseWord: CreateWord.Response?
            withContext(Dispatchers.IO){
                val request = CreateWord.Request(expression)
                responseWord = currentVocabId.let { wordRepository.createWord(it, request) }.data
            }
            if (responseWord != null) {
                withContext(Dispatchers.IO){
                    val request = CreateDef.Request(definition, type)
                    responseWord.wordId.let { defRepository.createDef(it, request) }
                }
            }
            loadWordsWithDefs()
        }
    }
    fun deleteWord(wordId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            wordRepository.deleteWord(wordId)
            loadWordsWithDefs() // 삭제 후 아이템 업데이트
        }
    }
}
