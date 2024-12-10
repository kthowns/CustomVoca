package com.example.customvoca.viewmodel

/*
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
}*/