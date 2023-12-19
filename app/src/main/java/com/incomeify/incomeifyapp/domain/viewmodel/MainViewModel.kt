package com.incomeify.incomeifyapp.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.incomeify.incomeifyapp.domain.model.RequestPredict
import com.incomeify.incomeifyapp.domain.repository.MainRepository

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    val message: LiveData<String> = repository.message
    val isLoading: LiveData<Boolean> = repository.isLoading
    val isError: LiveData<Boolean> = repository.isError
    val isSuccess: LiveData<Boolean> = repository.isSuccess
    val income: LiveData<Int> = repository.income

    fun predictIncome(requestBody: RequestPredict) {
        repository.predictIncome(requestBody)
    }
}