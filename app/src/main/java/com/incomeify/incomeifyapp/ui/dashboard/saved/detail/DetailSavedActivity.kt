package com.incomeify.incomeifyapp.ui.dashboard.saved.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.incomeify.incomeifyapp.R
import androidx.core.content.IntentCompat.getParcelableExtra
import com.incomeify.incomeifyapp.data.local.SavedIncome
import com.incomeify.incomeifyapp.data.session.SharedPreferencesManager
import com.incomeify.incomeifyapp.databinding.ActivityDetailSavedBinding
import com.incomeify.incomeifyapp.utils.Constants.DETAIL_SAVED

class DetailSavedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailSavedBinding
    private lateinit var sharedPreferencesManager: SharedPreferencesManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSavedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val savedIncome = getParcelableExtra(intent, DETAIL_SAVED, SavedIncome::class.java) as SavedIncome
        sharedPreferencesManager = SharedPreferencesManager(this)

        val username = sharedPreferencesManager.getUsername() ?: "Unknown"

        with(binding) {
            tvUsername.text = getString(R.string.username_text, username)
            tvIncome.text = getString(R.string.income_text, savedIncome.income)
            tvCareerLevel.text = savedIncome.careerLevel
            tvExperiences.text = getString(R.string.experiences_text, savedIncome.experienceLevel)
            tvEmploymentType.text = savedIncome.employmentType
            tvLocation.text = savedIncome.location
            tvEducation.text = savedIncome.educationLevel
        }
    }
}