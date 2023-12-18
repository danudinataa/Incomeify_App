package com.incomeify.incomeifyapp.ui.dashboard.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.incomeify.incomeifyapp.domain.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val appRepository: AppRepository
) : ViewModel() {

    private val _predictionResult = MutableLiveData<Int>()
    val predictionResult: LiveData<Int> get() = _predictionResult

    fun predictIncome(
        careerLevel: String,
        location: String,
        experienceLevel: Double,
        educationLevel: String,
        employmentType: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = appRepository.predictIncome(
                careerLevel,
                location,
                experienceLevel,
                educationLevel,
                employmentType
            )

            withContext(Dispatchers.Main) {
                _predictionResult.value = result
            }
        }
    }
}