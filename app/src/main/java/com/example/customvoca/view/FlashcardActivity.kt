package com.example.customvoca.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.customvoca.R
import com.example.customvoca.databinding.ActivityFlashcardBinding
import com.example.customvoca.dto.WordWithDef

class FlashcardActivity : AppCompatActivity() {

    private lateinit var wordList: List<WordWithDef> // List로 수정
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityFlashcardBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_flashcard
        )

        // intent에서 전달된 데이터를 가져와서 wordList에 할당
        val wordWithDefs = intent.getSerializableExtra("words") as? List<WordWithDef>
        wordWithDefs?.let {
            wordList = it
        } ?: run {
            wordList = emptyList() // null인 경우 빈 리스트로 초기화
        }

        // 초기 단어 표시
        showFlashcard(currentIndex, binding)

        // '이전' 버튼 클릭 시
        binding.btnPrevious.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                showFlashcard(currentIndex, binding)
            }
        }

        // '다음' 버튼 클릭 시
        binding.btnNext.setOnClickListener {
            if (currentIndex < wordList.size - 1) {
                currentIndex++
                showFlashcard(currentIndex, binding)
            }
        }

        // '뜻 보기' 버튼 클릭 시 정의 보이게 설정
        binding.btnShowDefinition.setOnClickListener {
            val currentWord = wordList[currentIndex]
            binding.textDefinition.setBackgroundColor(
                ContextCompat.getColor(this, R.color.white) // 배경색 정상으로 변경
            )
            binding.textDefinition.text = currentWord.definition + "("+currentWord.type+")" // 뜻 표시
        }
    }

    private fun showFlashcard(index: Int, binding: ActivityFlashcardBinding) {
        val word = wordList[index]
        binding.textExpression.text = word.expression
        binding.textDefinition.text = word.definition + " ("+ word.type+")"

        // '뜻 보기' 전까지는 배경을 검은색으로 설정하여 보이지 않도록 설정
        binding.textDefinition.setBackgroundColor(
            ContextCompat.getColor(this, R.color.black)
        )
        binding.textDefinition.text = "" // 정의는 숨김
    }
}
