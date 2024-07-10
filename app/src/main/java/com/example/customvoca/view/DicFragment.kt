package com.example.customvoca.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.customvoca.R
import com.example.customvoca.databinding.FragmentDicBinding
import com.example.customvoca.model.DicAdapter
import com.example.customvoca.viewmodel.DicViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        dicViewModel.currentDic.observe(viewLifecycleOwner){
            binding.titleDic.text = dicViewModel.getCurrentDicTitle()
            dicViewModel.updateItems()
        }
        loadArgsToViewModel()

        adapter = DicAdapter(dicViewModel)
        binding.recyclerViewDic.adapter = adapter
        binding.recyclerViewDic.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewDic.itemAnimator = null

        binding.swipeRecyclerDic.setOnRefreshListener {
            dicViewModel.updateItems()
        }

        dicViewModel.dicItems.observe(viewLifecycleOwner) { items ->
            binding.swipeRecyclerDic.isRefreshing = false
            adapter.updateItem(items)
        }

        return binding.root
    }
    override fun onResume() {
        super.onResume()
        if(dicViewModel.getCurrentDicId() != getArgs("dic_id").toInt()){
            loadArgsToViewModel()
        }
    }
    private fun loadArgsToViewModel(){
        arg = getArgs("dic_id")
        if(arg != ""){
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