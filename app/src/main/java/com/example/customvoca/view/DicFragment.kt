package com.example.customvoca.view

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.customvoca.R
import com.example.customvoca.database.Dic
import com.example.customvoca.database.Word
import com.example.customvoca.databinding.FragmentDicBinding
import com.example.customvoca.model.DicAdapter
import com.example.customvoca.viewmodel.DicViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class DicFragment : Fragment() {
    private val dicViewModel: DicViewModel by viewModels()
    private lateinit var binding: FragmentDicBinding
    private lateinit var adapter: DicAdapter
    private var arg: String = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dic, container, false)
        binding.dicViewModel = dicViewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        binding.btnAddWord.setOnClickListener{
            val word = binding.txtWord.text.toString()
            val meaning = binding.txtMeaning.text.toString()
            dicViewModel.addWord(word, meaning)
        }

        dicViewModel.currentDic.observe(viewLifecycleOwner){ dic ->
            onCurrentDicChange(dic)
        }

        dicViewModel.dicItems.observe(viewLifecycleOwner) { items ->
            onDicItemsChange(items)
        }

        loadArgsToViewModel()
        //Argument를 dicViewModel의 currentDic에 반영

        adapter = DicAdapter(dicViewModel)
        binding.recyclerViewDic.adapter = adapter
        binding.recyclerViewDic.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewDic.itemAnimator = null
        //Recycler Adapter 바인딩

        binding.swipeRecyclerDic.setOnRefreshListener {
            /*
                SwipeRefreshLayout 작동 시
             */
            dicViewModel.updateItems()
        }

        return binding.root
    }
    override fun onResume() {
        super.onResume()
        if(dicViewModel.getCurrentDicId() != getArgs("dic_id").toInt()){
            //args 값이 변경되었을 경우
            loadArgsToViewModel()
        }
    }
    private fun onDicItemsChange(items: List<Word>){
        /*
            dicViewModel의 dicItems가 업데이트 되었을 때
         */
        binding.swipeRecyclerDic.isRefreshing = false
        adapter.updateItem(items)
        binding.progressLoading.visibility = View.GONE
    }

    private fun onCurrentDicChange(dic: Dic){
        /*
            dicViewModel의 currentDic이 업데이트 되었을 때
         */
        binding.progressLoading.visibility = View.VISIBLE

        //로딩바 회전 로직

        binding.titleDic.text = dic.name
        dicViewModel.updateItems()
    }
    private fun loadArgsToViewModel(){
        arg = getArgs("dic_id")
        if(arg != ""){
            //args 값이 공백이 아닐 경우
            dicViewModel.setCurrentDic(arg.toInt())
        }
    }
    private fun getArgs(key: String): String{
        val result = arguments?.getString(key)
        if(result != null)
            return result
        return ""
    }
}