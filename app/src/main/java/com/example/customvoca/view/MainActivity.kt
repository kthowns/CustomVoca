package com.example.customvoca.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.customvoca.R
import com.example.customvoca.databinding.ActivityMainBinding
import com.example.customvoca.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil
            .setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        binding.btnAdmin.setOnClickListener{
            intent = Intent(applicationContext, AdminActivity::class.java)
            startActivity(intent)
        }
    }
}