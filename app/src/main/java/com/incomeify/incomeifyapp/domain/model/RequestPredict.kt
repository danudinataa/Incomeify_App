package com.incomeify.incomeifyapp.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RequestPredict(

    @field:SerializedName("career_level")
    val careerLevel: String,

    @field:SerializedName("location")
    val location: String,

    @field:SerializedName("experience_level")
    val experienceLevel: Double,

    @field:SerializedName("education_level")
    val educationLevel: String,

    @field:SerializedName("employment_type")
    val employmentType: String
) : Parcelable
