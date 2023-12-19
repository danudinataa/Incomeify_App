package com.incomeify.incomeifyapp.data.remote

import com.incomeify.incomeifyapp.utils.Constants.PREDICT_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIConfig {

    fun getPredictInstance() : APIServices {
        val retrofit = Retrofit.Builder().baseUrl(PREDICT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(APIServices::class.java)
    }
}