package com.incomeify.incomeifyapp.data.remote

import com.google.gson.GsonBuilder
import com.incomeify.incomeifyapp.utils.Constants.PREDICT_URL
import de.hdodenhof.circleimageview.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIConfig {

    fun getPredictInstance() : APIServices {
        val retrofit = Retrofit.Builder().baseUrl(PREDICT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(APIServices::class.java)
    }
    
    private const val baseUrl = "https://node-api-ikm4vmuapa-as.a.run.app/"

    fun getInstance(): APIServices {
        val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val gson = GsonBuilder().setLenient().create()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()

        return retrofit.create(APIServices::class.java)
    }


}
