package com.incomeify.incomeifyapp.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "saved_income")
@Parcelize
data class SavedIncome (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "career_level")
    val careerLevel: String,

    @ColumnInfo(name = "location")
    val location: String,

    @ColumnInfo(name = "experience_level")
    val experienceLevel: Double,

    @ColumnInfo(name = "education_level")
    val educationLevel: String,

    @ColumnInfo(name = "employment_type")
    val employmentType: String,

    @ColumnInfo(name = "income")
    val income: Int

) : Parcelable