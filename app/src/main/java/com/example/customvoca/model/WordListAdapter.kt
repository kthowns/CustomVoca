package com.example.customvoca.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.customvoca.R
import com.example.customvoca.dto.WordWithDef
import com.example.customvoca.viewmodel.WordListViewModel

class WordListAdapter(
    private val wordListViewModel: WordListViewModel
) : ListAdapter<WordWithDef, WordListAdapter.WordViewHolder>(DiffCallback()) {

    private var isEditMode = false // Edit mode 상태

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.wordlist_item, parent, false)
        return WordViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        // Edit 모드일 때 삭제, 편집 버튼 보이기/숨기기
        holder.item_btn_delete.visibility = if (isEditMode) View.VISIBLE else View.GONE
        holder.item_btn_edit.visibility = if (isEditMode) View.VISIBLE else View.GONE

        // 편집 버튼 클릭 시의 동작
        holder.item_btn_edit.setOnClickListener {
            // 편집 버튼 클릭 시 처리
            // 예: 편집 화면으로 이동
        }

        // 삭제 버튼 클릭 시의 동작
        holder.item_btn_delete.setOnClickListener {
            wordListViewModel.deleteWord(item.wordId) // 삭제 호출
        }

        // 아이템을 길게 눌렀을 때 edit 모드 토글
        holder.itemView.setOnLongClickListener {
            toggleEditMode()
            true
        }
    }

    inner class WordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val expressionTextView: TextView = view.findViewById(R.id.expressionTextView)
        val definitionTextView: TextView = view.findViewById(R.id.definitionTextView)
        val typeTextView: TextView = view.findViewById(R.id.typeTextView)
        val item_btn_delete: ImageButton = view.findViewById(R.id.item_btn_delete)
        val item_btn_edit: ImageButton = view.findViewById(R.id.item_btn_edit)

        fun bind(item: WordWithDef) {
            expressionTextView.text = item.expression
            definitionTextView.text = item.definition
            typeTextView.text = item.type
        }
    }

    // Edit mode 토글
    private fun toggleEditMode() {
        isEditMode = !isEditMode
        notifyDataSetChanged()
    }

    class DiffCallback : DiffUtil.ItemCallback<WordWithDef>() {
        override fun areItemsTheSame(oldItem: WordWithDef, newItem: WordWithDef): Boolean {
            return oldItem.expression == newItem.expression
        }

        override fun areContentsTheSame(oldItem: WordWithDef, newItem: WordWithDef): Boolean {
            return oldItem == newItem
        }
    }
}
