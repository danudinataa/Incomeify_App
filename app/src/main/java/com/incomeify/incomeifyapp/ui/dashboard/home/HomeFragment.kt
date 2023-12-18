package com.incomeify.incomeifyapp.ui.dashboard.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.incomeify.incomeifyapp.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

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

    }

    private fun submitPredict() {

        val careerLevel = binding.autoCompleteTextCareer.text.toString()
        val location = binding.autoCompleteTextLocation.text.toString()
        val experienceLevel = binding.etExperiences.text.toString()
        val educationLevel = binding.autoCompleteTextEducation.text.toString()
        val employmentType = binding.autoCompleteTextEmployment.text.toString()

        if (listOf(careerLevel, location, experienceLevel, educationLevel, employmentType).any { it.isBlank() }) {
            // TODO: dialog error 
            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
        } else {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    homeViewModel.predictIncome(
                        careerLevel = careerLevel,
                        location = location,
                        experienceLevel = experienceLevel.toDouble(),
                        educationLevel = educationLevel,
                        employmentType = employmentType
                    )

                    // TODO: go to income activity 
                }
            }
        }

    }


}