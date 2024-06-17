package com.example.customvoca.model

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.customvoca.R
import com.example.customvoca.database.Dic
import com.example.customvoca.database.Word
import com.example.customvoca.databinding.DiclistItemBinding
import com.example.customvoca.viewmodel.DicListViewModel
import com.example.customvoca.viewmodel.DicViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DicListAdapter(val dicListViewModel: DicListViewModel) : RecyclerView.Adapter<DicListAdapter.ViewHolder>(){
    private var itemList = listOf<Dic>()
    private var isEditMode = false
    private lateinit var binding: DiclistItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.diclist_item, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.item_dic_name.text = item.name
        holder.item_dic_num.text = item.dic_id.toString()
        holder.item_btn_delete.visibility = if(isEditMode) View.VISIBLE else View.GONE
        holder.item_btn_edit.visibility = if(isEditMode) View.VISIBLE else View.GONE
        holder.item_background.setOnLongClickListener{
            toggleEditMode()
            return@setOnLongClickListener true
        }
        holder.item_background.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("dic_id", item.dic_id.toString())
            it.findNavController().navigate(R.id.action_dicListFragment_to_dicFragment, bundle)
        }
        holder.item_btn_delete.setOnClickListener{
            dicListViewModel.dicListItems.postValue(dicListViewModel.dicListItems.value?.minus(item))
            dicListViewModel.deleteDic(item)
        }
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item_dic_name = itemView.findViewById<TextView>(R.id.item_dic_name)
        val item_background = itemView.findViewById<ConstraintLayout>(R.id.item_background)
        val item_dic_num = itemView.findViewById<TextView>(R.id.item_dic_num)
        val item_btn_delete = itemView.findViewById<ImageButton>(R.id.item_btn_delete)
        val item_btn_edit = itemView.findViewById<ImageButton>(R.id.item_btn_edit)
    }
    override fun getItemCount(): Int {
        return itemList.count()
    }
    fun updateItem(items: List<Dic>){
        itemList = items
        notifyDataSetChanged()
    }
    fun toggleEditMode(){
        isEditMode = !isEditMode
        notifyDataSetChanged()
    }
}