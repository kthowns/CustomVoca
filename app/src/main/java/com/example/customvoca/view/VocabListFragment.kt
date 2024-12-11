package com.example.customvoca.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.customvoca.R
import com.example.customvoca.databinding.FragmentVocablistBinding
import com.example.customvoca.model.VocabListAdapter
import com.example.customvoca.viewmodel.VocabListViewModel

class VocabListFragment : Fragment() {
    private val vocabListViewModel: VocabListViewModel by activityViewModels()
    private lateinit var binding: FragmentVocablistBinding
    private lateinit var adapter: VocabListAdapter
    private lateinit var loadingDialog: LoadingDialog
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_vocablist, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.vocabListViewModel = vocabListViewModel
        vocabListViewModel.setUId(arguments?.getInt("userId", 0))

        adapter = VocabListAdapter(vocabListViewModel)
        binding.recyclerViewVocablist.adapter = adapter
        binding.recyclerViewVocablist.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewVocablist.itemAnimator = null

        binding.btnCreateVocab.setOnClickListener {
            showCreateVocabDialog()
        }

        loadingDialog = LoadingDialog(requireContext())
        vocabListViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                loadingDialog.show()  // 로딩 시작 시 다이얼로그 표시
            } else {
                loadingDialog.close()  // 로딩 완료 시 다이얼로그 닫기
            }
        }

        vocabListViewModel.vocabListItems.observe(viewLifecycleOwner) { items ->
            binding.swipeRefreshRecycler.isRefreshing = false
            adapter.updateItem(items)
            Log.d("VocabListViewModel", "dicListItems Observed change" +  items)
        }

        binding.swipeRefreshRecycler.setOnRefreshListener {
            vocabListViewModel.updateItems()
        }
        return binding.root
    }

    class LoadingDialog(private val context: Context) {
        private var dialog: AlertDialog? = null

        // 로딩 다이얼로그 표시
        fun show() {
            val progressBar = ProgressBar(context)
            progressBar.isIndeterminate = true
            progressBar.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            dialog = AlertDialog.Builder(context)
                .setView(progressBar)
                .setCancelable(false) // 다이얼로그 외부 클릭 시 닫히지 않도록 설정
                .create()

            dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog?.show()
        }

        // 로딩 다이얼로그 닫기
        fun close() {
            dialog?.dismiss()
        }
    }

    private fun showCreateVocabDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_vocab, null)
        val titleEditText = dialogView.findViewById<EditText>(R.id.editTextTitle)
        val descEditText = dialogView.findViewById<EditText>(R.id.editTextDesc)

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("단어장 생성")
            .setView(dialogView)
            .setPositiveButton("확인") { _, _ ->
                val title = titleEditText.text.toString()
                val description = descEditText.text.toString()
                vocabListViewModel.createVocab(title, description)
            }
            .setNegativeButton("취소", null)
            .create()

        dialog.show()
    }
}