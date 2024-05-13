package com.example.customvoca.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.customvoca.R
import com.example.customvoca.database.Word

class RecyclerViewAdapter(var itemList: List<Word> = listOf()) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.word_word.text = itemList[position].word
        holder.word_meaning.text = itemList[position].meaning
    }
    override fun getItemCount(): Int {
        return itemList.count()
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val word_word = itemView.findViewById<TextView>(R.id.txtWord)
        val word_meaning = itemView.findViewById<TextView>(R.id.txtMeaning)
    }
    fun updateItem(items: List<Word>){
        itemList = items
        notifyDataSetChanged()
    }
}