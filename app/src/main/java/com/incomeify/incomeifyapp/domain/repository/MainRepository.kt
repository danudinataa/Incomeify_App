package com.incomeify.incomeifyapp.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.incomeify.incomeifyapp.data.remote.APIConfig
import com.incomeify.incomeifyapp.data.remote.APIServices
import com.incomeify.incomeifyapp.data.response.PredictResponse
import com.incomeify.incomeifyapp.domain.model.RequestPredict
import com.incomeify.incomeifyapp.utils.wrapEspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepository(
    private val apiService: APIServices
) {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    private val _income = MutableLiveData<Int>()
    val income: LiveData<Int> = _income

    fun predictIncome (requestBody: RequestPredict) {
        wrapEspressoIdlingResource {
            _isLoading.value = true
            val api = APIConfig.getPredictInstance().predict(requestBody)
            api.enqueue(object : Callback<PredictResponse> {

                override fun onResponse(
                    call: Call<PredictResponse>,
                    response: Response<PredictResponse>
                ) {
                    _isLoading.value = false
                    val responseBody = response.body()

                    if (response.isSuccessful) {
                        _income.value = responseBody?.prediction ?: 0
                        _message.value = responseBody?.status.toString()
                    } else {
                        _message.value = response.message()
                    }
                }

                override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
                    _isLoading.value = false
                    _message.value = t.message.toString()
                }
            })
        }
    }
}