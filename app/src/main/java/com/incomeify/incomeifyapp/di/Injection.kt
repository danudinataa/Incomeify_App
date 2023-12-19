package com.incomeify.incomeifyapp.di

import android.content.Context
import com.incomeify.incomeifyapp.data.remote.APIConfig
import com.incomeify.incomeifyapp.domain.repository.MainRepository

object Injection {

    fun provideRepository(context: Context): MainRepository {
        val apiService = APIConfig.getPredictInstance()
        return MainRepository(apiService)
    }
}