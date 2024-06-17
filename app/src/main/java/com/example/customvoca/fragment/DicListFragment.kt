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
import com.example.customvoca.databinding.FragmentDiclistBinding
import com.example.customvoca.model.DicAdapter
import com.example.customvoca.model.DicListAdapter
import com.example.customvoca.viewmodel.DicListViewModel

class DicListFragment : Fragment() {
    private val dicListViewModel: DicListViewModel by activityViewModels()
    private lateinit var binding: FragmentDiclistBinding
    private lateinit var adapter: DicListAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_diclist, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.dicListViewModel = dicListViewModel

        adapter = DicListAdapter(dicListViewModel)
        binding.recyclerViewDiclist.adapter = adapter
        binding.recyclerViewDiclist.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewDiclist.itemAnimator = null

        dicListViewModel.dicListItems.observe(viewLifecycleOwner, Observer { items ->
            binding.swipeRefreshRecycler.isRefreshing = false
            adapter.updateItem(items)
            Log.d("DicListViewModel", "dicListItems Observed change" +  items)
        })

        binding.swipeRefreshRecycler.setOnRefreshListener {
            dicListViewModel.updateItems()
        }
        return binding.root
    }
}