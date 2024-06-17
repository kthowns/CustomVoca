package com.example.customvoca.model

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.customvoca.R
import com.example.customvoca.database.Word
import com.example.customvoca.viewmodel.DicViewModel

class DicAdapter(val dicViewModel: DicViewModel) : RecyclerView.Adapter<DicAdapter.ViewHolder>(){
    val itemList = mutableListOf<Word>()
    var isEditMode = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("RecyclerView", "onCreateViewHolder Run")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dic_item, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("RecyclerView", "onBindViewHolder Run")
        val item = itemList[position]
        holder.item_word.text = item.word
        holder.item_meaning.text = item.meaning
        holder.item_btn_delete.visibility = if(isEditMode) View.VISIBLE else View.GONE
        holder.item_btn_edit.visibility = if(isEditMode) View.VISIBLE else View.GONE
        holder.item_background.setOnLongClickListener{
            toggleEditMode()
            return@setOnLongClickListener true
        }
        holder.item_btn_delete.setOnClickListener{
            dicViewModel.deleteWord(item)
        }
    }
    override fun getItemCount(): Int {
        return itemList.count()
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item_word = itemView.findViewById<TextView>(R.id.item_word)
        val item_meaning = itemView.findViewById<TextView>(R.id.item_meaning)
        val item_background = itemView.findViewById<ConstraintLayout>(R.id.item_background)
        val item_btn_delete = itemView.findViewById<ImageButton>(R.id.item_btn_delete)
        val item_btn_edit = itemView.findViewById<ImageButton>(R.id.item_btn_edit)
    }
    fun updateItem(items: List<Word>){
        Log.d("RecyclerView", "updateItem Run")
        itemList.clear()
        items.forEach{
            itemList.add(it)
        }
        notifyDataSetChanged()
    }
    fun toggleEditMode(){
        isEditMode = !isEditMode
        notifyDataSetChanged()
    }
}