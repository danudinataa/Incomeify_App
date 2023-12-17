package com.incomeify.incomeifyapp.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.incomeify.incomeifyapp.R
import com.incomeify.incomeifyapp.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            val navHostFragment = supportFragmentManager.findFragmentById(cvFragment.id) as NavHostFragment
            bottomNavigationView.setupWithNavController(navHostFragment.navController)
        }
    }
}