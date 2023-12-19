package com.incomeify.incomeifyapp.ui.dashboard.home.income

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat.getParcelableExtra
import com.incomeify.incomeifyapp.R
import com.incomeify.incomeifyapp.databinding.ActivityIncomeBinding
import com.incomeify.incomeifyapp.domain.model.RequestPredict
import com.incomeify.incomeifyapp.utils.Constants.INCOME
import com.incomeify.incomeifyapp.utils.Constants.REQUEST_BODY

class IncomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIncomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIncomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val requestBody = getParcelableExtra(intent, REQUEST_BODY, RequestPredict::class.java) as RequestPredict

        val income = intent.getIntExtra(INCOME, 0)

        with(binding) {
            tvIncome.text = getString(R.string.income_text, income)
            tvCareerLevel.text = requestBody.careerLevel
            tvExperiences.text = requestBody.experienceLevel.toString()
            tvEmploymentType.text = requestBody.employmentType
            tvLocation.text = requestBody.location
            tvEducation.text = requestBody.educationLevel
        }
    }
}