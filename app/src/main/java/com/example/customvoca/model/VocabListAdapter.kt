package com.example.customvoca.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.customvoca.R
import com.example.customvoca.dto.VocabDto
import com.example.customvoca.viewmodel.VocabListViewModel

class VocabListAdapter(val vocabListViewModel: VocabListViewModel) : RecyclerView.Adapter<VocabListAdapter.ViewHolder>() {
    private var itemList: List<VocabDto>? = listOf()
    private var isEditMode = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vocablist_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList?.get(position)
        holder.vocab_title.text = item?.title // vocab_title 사용
        holder.vocab_description.text = item?.description // vocab_description 사용
        holder.item_dic_num.text = item?.vocabId.toString() + ". "

        // Edit/Delete 버튼 표시 여부
        holder.item_btn_delete.visibility = if (isEditMode) View.VISIBLE else View.GONE
        holder.item_btn_edit.visibility = if (isEditMode) View.VISIBLE else View.GONE

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            if (item != null) {
                bundle.putInt("vocabId", item.vocabId)
            }
            it.findNavController().navigate(R.id.action_vocabListFragment_to_wordListFragment, bundle)
        }

        holder.itemView.setOnLongClickListener {
            toggleEditMode()
            true
        }

        holder.item_btn_delete.setOnClickListener {
            if (item != null) {
                vocabListViewModel.deleteVocab(item.vocabId)
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val vocab_title = itemView.findViewById<TextView>(R.id.vocab_title) // vocab_title
        val vocab_description = itemView.findViewById<TextView>(R.id.vocab_description) // vocab_description
        val item_dic_num = itemView.findViewById<TextView>(R.id.item_dic_num)
        val item_btn_delete = itemView.findViewById<ImageButton>(R.id.item_btn_delete)
        val item_btn_edit = itemView.findViewById<ImageButton>(R.id.item_btn_edit)
    }

    override fun getItemCount(): Int {
        return itemList?.count() ?: 0
    }

    fun updateItem(items: List<VocabDto>?) {
        itemList = items
        notifyDataSetChanged()
    }

    fun toggleEditMode() {
        isEditMode = !isEditMode
        notifyDataSetChanged()
    }
}