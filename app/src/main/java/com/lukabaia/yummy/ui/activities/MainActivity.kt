package com.lukabaia.yummy.ui.activities

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.ui.setupWithNavController
import com.lukabaia.yummy.R
import com.lukabaia.yummy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

    }

    private fun init() {
        binding.bottomNavigationView.setupWithNavController(findNavController(R.id.fragmentContainerView))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}