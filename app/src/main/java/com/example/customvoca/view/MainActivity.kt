package com.example.customvoca.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.customvoca.R
import com.example.customvoca.databinding.ActivityMainBinding
import com.example.customvoca.viewmodel.MainViewModel
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.customvoca.repository.VocabRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val vocabRepository = VocabRepository()
        lifecycleScope.launch(Dispatchers.IO){
            Log.d("VocabRepository", vocabRepository.getVocabs(1).toString())
        }

        binding = DataBindingUtil
            .setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
/*
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationView.itemIconTintList = null;
        binding.bottomNavigationView.setupWithNavController(navController)

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.navmenu_previous -> {
                    navController.popBackStack()
                }
                else -> navController.navigate(item.itemId)
            }
            true
        }
 */
    }
}