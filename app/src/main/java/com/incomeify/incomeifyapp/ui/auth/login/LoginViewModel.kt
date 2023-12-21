package com.incomeify.incomeifyapp.ui.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.incomeify.incomeifyapp.data.remote.APIConfig
import com.incomeify.incomeifyapp.domain.model.RequestGoogle
import com.incomeify.incomeifyapp.domain.model.RequestLogin
import kotlinx.coroutines.Dispatchers

class LoginViewModel : ViewModel() {
    fun loginUser(email: String, password: String) = liveData(Dispatchers.IO) {
        val requestBody = RequestLogin(email, password)
        try {
            val response = APIConfig.getInstance().loginUser(requestBody).execute()
            if (response.isSuccessful && response.body()?.success == true) {
                emit(Result.success(response.body()))
            } else {
                emit(Result.failure(RuntimeException("Email atau password anda salah")))
            }
        } catch (e: Exception) {
            emit(Result.failure(RuntimeException("Email atau password anda salah")))
        }
    }

    fun googleLogin() = liveData(Dispatchers.IO) {
        try {
            val response = APIConfig.getInstance().googleLogin().execute()
            Log.d("LoginViewModel", "Response: $response")
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    Log.d("LoginViewModel", "Login success with response: $responseBody")
                    emit(Result.success("Welcome to Incomeify"))
                } else {
                    Log.d("LoginViewModel", "Login failed: Response body is null")
                    emit(Result.failure(RuntimeException("Failed to login with Google")))
                }
            } else {
                Log.d("LoginViewModel", "Login failed: Response unsuccessful")
                emit(Result.failure(RuntimeException("Failed to login with Google")))
            }
        } catch (e: Exception) {
            Log.d("LoginViewModel", "Login failed: ${e.message}")
            e.printStackTrace()
            emit(Result.failure(RuntimeException("Network error")))
        }
    }

    fun getUserData(token: String) = liveData(Dispatchers.IO) {
        try {
            val response = APIConfig.getInstance().getUserData("Bearer $token").execute()
            if (response.isSuccessful) {
                emit(Result.success(response.body()?.data))
            } else {
                emit(Result.failure(RuntimeException("Gagal mengambil data pengguna")))
            }
        } catch (e: Exception) {
            emit(Result.failure(RuntimeException("Kesalahan jaringan")))
        }
    }
}