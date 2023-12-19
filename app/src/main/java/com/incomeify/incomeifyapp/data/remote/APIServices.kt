package com.incomeify.incomeifyapp.data.remote

import com.incomeify.incomeifyapp.data.response.PredictResponse
import com.incomeify.incomeifyapp.domain.model.RequestPredict
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface APIServices {

    @POST("predict")
    @Headers("Content-Type: application/json;charset=UTF-8")
    fun predict (@Body body: RequestPredict): Call<PredictResponse>

}
