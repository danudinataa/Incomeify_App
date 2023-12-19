package com.incomeify.incomeifyapp.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PredictResponse (

    @field:SerializedName("prediction")
    val prediction: Int,

    @field:SerializedName("status")
    val status: Boolean
) : Parcelable