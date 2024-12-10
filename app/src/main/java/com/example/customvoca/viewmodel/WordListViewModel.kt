package com.example.customvoca.viewmodel

/*
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
}*/