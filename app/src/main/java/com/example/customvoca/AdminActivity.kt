package com.example.customvoca

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.forEach
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.customvoca.database.Dic
import com.example.customvoca.database.Word
import com.example.customvoca.databinding.ActivityAdminBinding
import com.example.customvoca.model.VocaDatabase
import com.example.customvoca.model.VocaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdminActivity: AppCompatActivity() {
    private var tableType = 0 //0:Dic, 1:Word
    private lateinit var binding: ActivityAdminBinding
    private lateinit var vocaRepository: VocaRepository
    private lateinit var itemsDic: MutableList<Dic>
    private lateinit var itemsWord: MutableList<Word>
    private lateinit var adapterDic: ArrayAdapter<Dic>
    private lateinit var adapterWord: ArrayAdapter<Word>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_admin)
        binding.lifecycleOwner = this
        vocaRepository = VocaRepository.getInstance(application)
        tableType = 0

        itemsDic = mutableListOf()
        itemsWord = mutableListOf()
        adapterDic = ArrayAdapter<Dic>(this, android.R.layout.simple_list_item_multiple_choice, itemsDic)
        adapterWord = ArrayAdapter<Word>(this, android.R.layout.simple_list_item_multiple_choice, itemsWord)
        binding.listView1.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        updateListView()

        binding.listView1.setOnItemClickListener { parent, view, position, id ->
            val checked = binding.listView1.isItemChecked(position)
            binding.listView1.setItemChecked(position, checked)
        }
        binding.rgTable.setOnCheckedChangeListener{ group, i ->
            tableChange(i)
        }
        binding.btnAdd.setOnClickListener{
            lifecycleScope.launch {
                if (tableType == 0) {
                    var dic = Dic(binding.textList.text.toString())
                    withContext(Dispatchers.IO) {
                        vocaRepository.insertDic(dic)
                    }
                    updateListView()
                } else if (tableType == 1) {
                    val str = arrayOf(
                        binding.textListNum.text.toString(),
                        binding.textWord.text.toString(), binding.textMeaning.text.toString()
                    )
                    withContext(Dispatchers.IO) {
                        vocaRepository.insertWord(Word(str[1], str[2], str[0].toInt()))
                    }
                    updateListView()
                }
            }
        }

        binding.btnDelete.setOnClickListener{
            lifecycleScope.launch {
                var positions = binding.listView1.checkedItemPositions
                var checkedItems = mutableListOf<Int>()
                positions.forEach { key, value ->
                    checkedItems.add(
                        binding.listView1.getItemAtPosition(key).toString().split('/')[0].toInt()
                    )
                }
                checkedItems.forEach {
                    binding.listView1.setItemChecked(it, false)
                }
                withContext(Dispatchers.IO) {
                    checkedItems.forEach {
                        if (tableType == 0) {
                            vocaRepository.deleteDic(it)
                        } else if (tableType == 1) {
                            vocaRepository.deleteWord(it)
                        }
                    }
                }
                updateListView()
            }
        }
        binding.btnExit.setOnClickListener{
            finish()
        }
    }
    fun tableChange(i: Int){
        if(i == binding.rbDic.id){
            tableType = 0
            binding.listView1.adapter = adapterDic
            updateListView()
            binding.textList.visibility = View.VISIBLE
            binding.textWord.visibility = View.GONE
            binding.textMeaning.visibility = View.GONE
            binding.textListNum.visibility = View.GONE
        }else{
            tableType = 1
            binding.listView1.adapter = adapterWord
            updateListView()
            binding.textList.visibility = View.GONE
            binding.textWord.visibility = View.VISIBLE
            binding.textMeaning.visibility = View.VISIBLE
            binding.textListNum.visibility = View.VISIBLE
        }
    }
    fun updateListView(){
        //db에서 데이터 가져온다
        //setAdapter 한다
        //notifyDataSetChanged() 한다
        lifecycleScope.launch(Dispatchers.Main) {
            if (tableType == 0) {
                withContext(Dispatchers.IO) {
                    itemsDic.clear()
                    vocaRepository.getDicAll().forEach {
                        itemsDic.add(it)
                    }
                }
                binding.listView1.adapter = adapterDic
                adapterWord.notifyDataSetChanged()

            } else if (tableType == 1) {
                withContext(Dispatchers.IO) {
                    itemsWord.clear()
                    vocaRepository.getWordAll().forEach {
                        itemsWord.add(it)
                    }
                }
                binding.listView1.adapter = adapterWord
                adapterDic.notifyDataSetChanged()
            }
        }
    }
}