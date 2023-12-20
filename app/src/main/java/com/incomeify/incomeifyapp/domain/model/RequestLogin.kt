package com.incomeify.incomeifyapp.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestLogin(

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("password")
    val password: String,

    ) : Parcelable