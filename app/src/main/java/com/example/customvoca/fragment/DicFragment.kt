package com.example.customvoca.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.customvoca.R
import com.example.customvoca.databinding.FragmentDicBinding
import com.example.customvoca.model.RecyclerViewAdapter
import com.example.customvoca.viewmodel.DicViewModel

class DicFragment : Fragment() {
    private val dicViewModel: DicViewModel by viewModels()
    private lateinit var binding: FragmentDicBinding
    private lateinit var adapter: RecyclerViewAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dic, container, false)
        binding.dicViewModel = dicViewModel
        binding.lifecycleOwner = this.viewLifecycleOwner

        adapter = RecyclerViewAdapter(dicViewModel)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.itemAnimator = null

        dicViewModel.recyclerItems.observe(viewLifecycleOwner, Observer { items ->
            adapter.updateItem(items)
        })
        return binding.root
    }
}