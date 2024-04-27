package com.example.customvoca

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.customvoca.databinding.ActivityVocasBinding
import com.example.customvoca.viewmodel.DatabaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VocasActivity : AppCompatActivity() {
    private val databaseViewModel: DatabaseViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil
            .setContentView<ActivityVocasBinding>(this, R.layout.activity_vocas)
        binding.lifecycleOwner = this
        binding.databaseViewModel = databaseViewModel
    }
}