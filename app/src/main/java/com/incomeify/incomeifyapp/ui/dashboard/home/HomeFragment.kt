package com.incomeify.incomeifyapp.ui.dashboard.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.incomeify.incomeifyapp.R
import com.incomeify.incomeifyapp.data.session.SharedPreferencesManager
import com.incomeify.incomeifyapp.databinding.FragmentHomeBinding
import com.incomeify.incomeifyapp.domain.model.RequestPredict
import com.incomeify.incomeifyapp.domain.viewmodel.MainViewModel
import com.incomeify.incomeifyapp.domain.viewmodel.ViewModelFactory
import com.incomeify.incomeifyapp.ui.customview.CustomDialog
import com.incomeify.incomeifyapp.ui.dashboard.home.income.IncomeActivity
import com.incomeify.incomeifyapp.utils.Constants.INCOME
import com.incomeify.incomeifyapp.utils.Constants.REQUEST_BODY

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    private val homeViewModel : MainViewModel by lazy {
        ViewModelProvider(this, ViewModelFactory(requireContext()))[MainViewModel::class.java]
    }

    private lateinit var requestBody: RequestPredict

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferencesManager = SharedPreferencesManager(requireContext())

        val username = sharedPreferencesManager.getUsername() ?: "Unknown"

        with(binding) {
            tvUsername.text = getString(R.string.username_text, username)
            btnSubmit.setOnClickListener { submitPredict() }
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        homeViewModel.isError.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                CustomDialog(
                    requireContext(),
                    getString(R.string.error_submit),
                    R.raw.error_anim).show()
            }
        }

        homeViewModel.income.observe(viewLifecycleOwner) { income ->
            if (income != null) {
                Log.d("Income", "Received income: $income")

                val intent = Intent(requireContext(), IncomeActivity::class.java)
                intent.putExtra(REQUEST_BODY, requestBody)
                intent.putExtra(INCOME, income)

                startActivity(intent)
            }
        }
    }
    private fun submitPredict() {

        val careerLevel = binding.autoCompleteTextCareer.text.toString()
        val location = binding.autoCompleteTextLocation.text.toString()
        val experienceLevel = binding.etExperiences.text.toString()
        val educationLevel = binding.autoCompleteTextEducation.text.toString()
        val employmentType = binding.autoCompleteTextEmployment.text.toString()

        if (listOf(careerLevel, location, experienceLevel, educationLevel, employmentType).any { it.isBlank() }) {
            CustomDialog(
                requireContext(),
                getString(R.string.error_input),
                R.raw.error_anim).show()
        } else {
            requestBody = RequestPredict(
                careerLevel = careerLevel,
                location = location,
                experienceLevel = experienceLevel.toDouble(),
                educationLevel = educationLevel,
                employmentType = employmentType
            )
            homeViewModel.predictIncome(requestBody)
        }
    }
}