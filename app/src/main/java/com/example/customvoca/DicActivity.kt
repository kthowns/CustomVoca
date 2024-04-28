package com.example.customvoca

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.customvoca.databinding.ActivityDicBinding
import com.example.customvoca.viewmodel.DatabaseViewModel

class DicActivity : AppCompatActivity() {
    private val databaseViewModel: DatabaseViewModel by viewModels()
    private lateinit var binding: ActivityDicBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil
            .setContentView(this, R.layout.activity_dic)
        binding.lifecycleOwner = this
        binding.databaseViewModel = databaseViewModel
    }
}