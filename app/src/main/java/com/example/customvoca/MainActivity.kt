package com.example.customvoca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.customvoca.databinding.ActivityMainBinding
import com.example.customvoca.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil
            .setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        findViewById<Button>(R.id.btnTest).setOnClickListener{
            val intent = Intent(this, VocasActivity::class.java)
            startActivity(intent)
        }
    }
}