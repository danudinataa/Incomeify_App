package com.incomeify.incomeifyapp.domain.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.incomeify.incomeifyapp.domain.repository.SavedRepository

class SavedViewModelFactory(private val savedRepository: SavedRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SavedViewModel(savedRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
