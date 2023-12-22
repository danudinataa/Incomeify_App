package com.incomeify.incomeifyapp.domain.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.incomeify.incomeifyapp.data.remote.APIConfig
import com.incomeify.incomeifyapp.domain.model.RequestRegister
import kotlinx.coroutines.Dispatchers
import android.util.Log

class RegisterViewModel : ViewModel() {

    fun registerUser(name: String, email: String, password: String) = liveData(Dispatchers.IO) {
        val requestBody = RequestRegister(name, email, password)
        Log.d("RegisterViewModel", "Sending registration request for: $email")
        try {
            val response = APIConfig.getInstance().registerUser(requestBody).execute()
            if (response.isSuccessful && response.body()?.success == true) {
                Log.d("RegisterViewModel", "API response successful for: $email")
                emit(Result.success("Akun berhasil didaftarkan"))
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                val errorMessage = if (response.code() == 400) {
                    "Email sudah terdaftar"
                } else {
                    errorBody
                }
                Log.d("RegisterViewModel", "UNSUCCESSFUL: $errorBody")
                emit(Result.failure(RuntimeException(errorMessage)))
            }
        } catch (e: Exception) {
            val errorMessage = "Error parsing error message"
            Log.d("RegisterViewModel", "onResponse: $errorMessage")
            emit(Result.failure(RuntimeException(errorMessage)))
        }
    }
}
