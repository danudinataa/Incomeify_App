package com.incomeify.incomeifyapp.data.remote

import com.incomeify.incomeifyapp.data.response.PredictResponse
import com.incomeify.incomeifyapp.domain.model.RequestPredict
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import com.incomeify.incomeifyapp.data.response.LoginResponse
import com.incomeify.incomeifyapp.data.response.RegisterResponse
import com.incomeify.incomeifyapp.domain.model.RequestLogin
import com.incomeify.incomeifyapp.domain.model.RequestRegister
import com.incomeify.incomeifyapp.domain.model.RequestGoogle
import com.incomeify.incomeifyapp.data.response.UserResponse
import retrofit2.http.*

interface APIServices {
    @POST("predict")
    @Headers("Content-Type: application/json;charset=UTF-8")
    fun predict (@Body body: RequestPredict): Call<PredictResponse>
    @POST("auth/login")
    @Headers("Content-Type: application/json;charset=UTF-8")
    fun loginUser(@Body body: RequestLogin ): Call<LoginResponse>

    @POST("auth/register")
    @Headers("Content-Type: application/json;charset=UTF-8")
    fun registerUser(@Body body: RequestRegister ): Call<RegisterResponse>

    @GET("api/user")
    fun getUserData(@Header("Authorization") token: String): Call<UserResponse>
}
