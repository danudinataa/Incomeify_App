package com.incomeify.incomeifyapp.ui.dashboard.home.income

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat.getParcelableExtra
import androidx.lifecycle.ViewModelProvider
import com.incomeify.incomeifyapp.R
import com.incomeify.incomeifyapp.data.local.SavedIncome
import com.incomeify.incomeifyapp.databinding.ActivityIncomeBinding
import com.incomeify.incomeifyapp.domain.model.RequestPredict
import com.incomeify.incomeifyapp.domain.repository.SavedRepository
import com.incomeify.incomeifyapp.domain.viewmodel.SavedViewModel
import com.incomeify.incomeifyapp.domain.viewmodel.SavedViewModelFactory
import com.incomeify.incomeifyapp.ui.customview.CustomDialog
import com.incomeify.incomeifyapp.utils.Constants.INCOME
import com.incomeify.incomeifyapp.utils.Constants.REQUEST_BODY

class IncomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIncomeBinding
    private val savedRepository: SavedRepository by lazy {
        SavedRepository.getInstance(this)
    }
    private val savedViewModel: SavedViewModel by lazy {
        ViewModelProvider(this, SavedViewModelFactory(savedRepository))[SavedViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIncomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val requestBody = getParcelableExtra(intent, REQUEST_BODY, RequestPredict::class.java) as RequestPredict

        val income = intent.getIntExtra(INCOME, 0)

        with(binding) {
            // TODO: Binding username 
            tvIncome.text = getString(R.string.income_text, income)
            tvCareerLevel.text = requestBody.careerLevel
            tvExperiences.text = requestBody.experienceLevel.toString()
            tvEmploymentType.text = requestBody.employmentType
            tvLocation.text = requestBody.location
            tvEducation.text = requestBody.educationLevel

            btnSaved.setOnClickListener {
                val savedIncome = SavedIncome(
                    income = income,
                    careerLevel = requestBody.careerLevel,
                    location = requestBody.location,
                    experienceLevel = requestBody.experienceLevel,
                    educationLevel = requestBody.educationLevel,
                    employmentType = requestBody.employmentType
                )
                savedViewModel.insertSavedIncome(savedIncome)
                showSuccessDialog()
            }

        }
    }

    private fun showSuccessDialog() {
        CustomDialog(
            this,
            getString(R.string.success_saved),
            R.raw.saved_anim).show()
    }
}