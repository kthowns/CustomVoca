package com.example.customvoca.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.customvoca.R
import com.example.customvoca.databinding.FragmentDicBinding
import com.example.customvoca.model.DicAdapter
import com.example.customvoca.viewmodel.DicListViewModel
import com.example.customvoca.viewmodel.DicViewModel

class DicFragment : Fragment() {
    private val dicListViewModel: DicListViewModel by activityViewModels()
    private val dicViewModel: DicViewModel by viewModels()
    private lateinit var binding: FragmentDicBinding
    private lateinit var adapter: DicAdapter
    private var arg: String = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dic, container, false)
        binding.dicViewModel = dicViewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        arg = getArgs("dic_id")
        if(arg != "")
            dicViewModel.currentDic = arg.toInt()

        adapter = DicAdapter(dicViewModel)
        binding.recyclerViewDic.adapter = adapter
        binding.recyclerViewDic.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewDic.itemAnimator = null

        dicViewModel.dicItems.observe(viewLifecycleOwner, Observer { items ->
            adapter.updateItem(items)
        })
        return binding.root
    }
    fun getArgs(key: String): String{
        val result = arguments?.getString(key)
        if(result != null)
            return result
        return ""
    }
    override fun onResume() {
        super.onResume()
        binding.textDic.text = dicViewModel.currentDic.toString()
        if(dicViewModel.currentDic != getArgs("dic_id").toInt()){
            dicViewModel.updateItems()
        }
    }
}