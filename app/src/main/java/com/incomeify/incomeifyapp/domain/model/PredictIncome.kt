package com.incomeify.incomeifyapp.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PredictIncome (

    @field:SerializedName("prediction")
    val prediction: Int,

    @field:SerializedName("statur")
    val status: Boolean
) : Parcelable