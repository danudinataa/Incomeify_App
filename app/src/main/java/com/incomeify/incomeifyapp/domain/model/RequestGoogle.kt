package com.incomeify.incomeifyapp.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RequestGoogle(

    @field:SerializedName("token")
    val token: String

) : Parcelable
