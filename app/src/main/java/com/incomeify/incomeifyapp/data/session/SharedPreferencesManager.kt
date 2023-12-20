package com.incomeify.incomeifyapp.data.session

import android.content.Context
import android.util.Log

class SharedPreferencesManager(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("MySharedPref", Context.MODE_PRIVATE)

    fun saveAuthToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString("AUTH_TOKEN", token)
        editor.apply()
        Log.d("SharedPreferences", "Token saved: $token")
    }

    fun getAuthToken(): String? {
        val token = sharedPreferences.getString("AUTH_TOKEN", null)
        Log.d("SharedPreferences", "Token retrieved: $token")
        return token
    }

    fun clearAuthToken() {
        val editor = sharedPreferences.edit()
        editor.remove("AUTH_TOKEN")
        editor.apply()
    }

    fun saveUsername(username: String) {
        val editor = sharedPreferences.edit()
        editor.putString("USERNAME", username)
        editor.apply()
    }

    fun saveEmail(email: String) {
        val editor = sharedPreferences.edit()
        editor.putString("EMAIL", email)
        editor.apply()
    }

    fun getUserId(): String? {
        return sharedPreferences.getString("USER_ID", null)
    }

    fun getUsername(): String? {
        return sharedPreferences.getString("USERNAME", null)
    }

    fun getEmail(): String? {
        return sharedPreferences.getString("EMAIL", null)
    }
}
