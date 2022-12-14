package com.lukabaia.yummy.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.navigation.findNavController
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
        window.setBackgroundDrawableResource(R.drawable.default_background)
        init()
    }

    private fun init() {
        binding.bottomNavigationView.setupWithNavController(findNavController(R.id.fragmentContainerView))
    }

    fun disableNavBar() {
        binding.bottomNavigationView.menu.forEach {
            it.isEnabled = false
        }
    }

    fun enableNavBar() {
        binding.bottomNavigationView.menu.forEach {
            it.isEnabled = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}