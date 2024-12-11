package com.example.customvoca.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.customvoca.R
import com.example.customvoca.databinding.FragmentWordlistBinding
import com.example.customvoca.model.WordListAdapter
import com.example.customvoca.repository.DefRepository
import com.example.customvoca.repository.VocabRepository
import com.example.customvoca.repository.WordRepository
import com.example.customvoca.type.POS
import com.example.customvoca.viewmodel.WordListViewModel

class WordListFragment : Fragment() {
    private var _binding: FragmentWordlistBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: WordListAdapter
    private lateinit var loadingDialog: LoadingDialog

    private val wordListViewModel: WordListViewModel by viewModels {
        WordListViewModel.Factory(
            WordRepository(), // WordRepository 인스턴스 주입
            DefRepository(),   // DefRepository 인스턴스 주입
            VocabRepository()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWordlistBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        loadingDialog = LoadingDialog(requireContext())
        wordListViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                loadingDialog.show()  // 로딩 시작 시 다이얼로그 표시
            } else {
                loadingDialog.close()  // 로딩 완료 시 다이얼로그 닫기
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView 설정
        adapter = WordListAdapter(wordListViewModel)
        binding.recyclerViewWordList.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewWordList.adapter = adapter

        // ViewModel 관찰
        wordListViewModel.wordWithDefs.observe(viewLifecycleOwner, Observer { wordWithDefs ->
            adapter.submitList(wordWithDefs)
        })

        wordListViewModel.currentDicTitle.observe(viewLifecycleOwner, Observer { title ->
            binding.titleVocab.text = title
        })

        binding.btnFlashcard.setOnClickListener{
            val intent = Intent(requireContext(), FlashcardActivity::class.java)
            intent.putExtra("words",
                wordListViewModel.wordWithDefs.value?.let { it1 -> ArrayList(it1) })  // 예시로 값 전달
            startActivity(intent)
        }

        binding.btnCreateWord.setOnClickListener{
            showCreateWordDialog()
        }

        // 데이터 로드
        val vocabId = getVocabId() // 필요한 vocabId를 가져오는 메서드
        wordListViewModel.currentVocabId = vocabId
        Log.d("vocabid", vocabId.toString())
        wordListViewModel.loadWordsWithDefs()

        // SwipeRefreshLayout 설정
        binding.swipeRecyclerWordList.setOnRefreshListener {
            wordListViewModel.loadWordsWithDefs()
            binding.swipeRecyclerWordList.isRefreshing = false
        }
    }

    private fun getVocabId(): Int {
        // 예시: 번들에서 vocabId를 가져오는 로직
        return arguments?.getInt("vocabId") ?: 0
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

    // 다이얼로그 빌더 생성
    private fun showCreateWordDialog() {
        // 다이얼로그 레이아웃 인플레이터
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_word, null)

        // EditText 및 Spinner 참조
        val editTextExpression = dialogView.findViewById<EditText>(R.id.editTextExpression)
        val editTextDefinition = dialogView.findViewById<EditText>(R.id.editTextDefinition)
        val spinnerPOS = dialogView.findViewById<Spinner>(R.id.spinnerPOS)

        // POS enum 값들을 Spinner에 세팅
        val posValues = POS.values().map { it.type } // POS 타입 문자열 값 리스트 생성
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            posValues
        ) // requireContext() 사용
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // 드롭다운 뷰 설정
        spinnerPOS.adapter = adapter

        // 다이얼로그 빌더 생성
        val builder = AlertDialog.Builder(requireContext()) // requireContext() 사용
            .setTitle("단어 추가")
            .setView(dialogView)
            .setPositiveButton("추가") { dialog, which ->
                // 사용자 입력값 받아오기
                val expression = editTextExpression.text.toString()
                val definition = editTextDefinition.text.toString()
                val selectedPOSName = spinnerPOS.selectedItem.toString()

                // POS의 name으로부터 key값 가져오기
                val selectedPOS = POS.fromName(selectedPOSName)
                Log.d("selectedPOS", selectedPOSName)

                // 입력 값이 유효한지 체크
                if (expression.isNotEmpty() && definition.isNotEmpty() && selectedPOS != null) {
                    // 입력 받은 값으로 단어 추가 로직 실행
                    wordListViewModel.createWord(
                        expression,
                        definition,
                        selectedPOS
                    ) // POS key는 name을 사용
                } else {
                    Toast.makeText(requireContext(), "모든 필드를 채워주세요", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("취소") { dialog, which ->
                dialog.dismiss() // 다이얼로그 취소
            }

        builder.create().show() // 다이얼로그 표시
    }
}