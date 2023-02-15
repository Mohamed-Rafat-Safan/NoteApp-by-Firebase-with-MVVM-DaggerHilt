package com.mohamedrafat.firebasewithmvvmapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.mohamedrafat.firebasewithmvvmapp.R
import com.mohamedrafat.firebasewithmvvmapp.databinding.ActivityMainBinding
import com.mohamedrafat.firebasewithmvvmapp.ui.NoteFragments.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}