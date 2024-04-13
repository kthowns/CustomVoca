package com.example.customvoca

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.customvoca.databinding.ActivityMainBinding
import com.example.customvoca.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        val binding = DataBindingUtil
            .setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        findViewById<Button>(R.id.btnTest).setOnClickListener{
            val intent = Intent(this, VocasActivity::class.java)
            startActivity(intent)
        }
    }
}