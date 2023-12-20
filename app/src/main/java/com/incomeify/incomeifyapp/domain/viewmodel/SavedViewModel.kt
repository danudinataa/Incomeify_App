package com.incomeify.incomeifyapp.domain.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.incomeify.incomeifyapp.data.local.SavedIncome
import com.incomeify.incomeifyapp.domain.repository.SavedRepository

class SavedViewModel(private val savedRepository: SavedRepository): ViewModel() {

    private val _savedIncomeList = MutableLiveData<List<SavedIncome>>()

    fun getSavedIncomeList(): LiveData<List<SavedIncome>> = savedRepository.getIncomes()

    fun insertSavedIncome(newSavedIncome: SavedIncome) = savedRepository.insertIncome(newSavedIncome)
}