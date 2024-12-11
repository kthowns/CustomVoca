package com.example.customvoca.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.customvoca.R
import com.example.customvoca.dto.LoginDto
import com.example.customvoca.repository.UserRepository
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val userRepository = UserRepository()
        val id = findViewById<EditText>(R.id.editTextId)
        val pass = findViewById<EditText>(R.id.editTextPassword)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val loadingDialog = LoadingDialog(this)

        loginButton.setOnClickListener{
            loadingDialog.show()
            lifecycleScope.launch{
                val request = LoginDto.Request(id.text.toString()
                    , pass.text.toString())
                val response = userRepository.login(request)
                loadingDialog.close()
                if(response.status == 200){
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.putExtra("userId", response.data?.userId)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(applicationContext, response.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
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
}