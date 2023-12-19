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
import com.incomeify.incomeifyapp.databinding.FragmentHomeBinding
import com.incomeify.incomeifyapp.domain.model.RequestPredict
import com.incomeify.incomeifyapp.domain.viewmodel.MainViewModel
import com.incomeify.incomeifyapp.domain.viewmodel.ViewModelFactory
import com.incomeify.incomeifyapp.ui.dashboard.home.income.IncomeActivity
import com.incomeify.incomeifyapp.utils.Constants.INCOME
import com.incomeify.incomeifyapp.utils.Constants.REQUEST_BODY

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

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


        with(binding) {
            btnSubmit.setOnClickListener { submitPredict() }
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            // TODO: binding loading logic
        }

        homeViewModel.isError.observe(viewLifecycleOwner) { isError ->
            if (isError) {
                // TODO: custom error dialog
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

        requestBody = RequestPredict(
            careerLevel = careerLevel,
            location = location,
            experienceLevel = experienceLevel.toDouble(),
            educationLevel = educationLevel,
            employmentType = employmentType
        )

        if (listOf(careerLevel, location, experienceLevel, educationLevel, employmentType).any { it.isBlank() }) {
            // TODO: dialog error 
            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
        } else {

            homeViewModel.predictIncome(requestBody)
        }
    }
}