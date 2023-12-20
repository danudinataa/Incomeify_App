package com.incomeify.incomeifyapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface SavedIncomeDao {

    @Query("SELECT * FROM saved_income")
    fun getIncomes(): LiveData<List<SavedIncome>>

    @Query("SELECT * FROM saved_income WHERE id = :savedId")
    fun getIncomeById(savedId: Int): LiveData<SavedIncome>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIncome(savedIncome: SavedIncome): Long

    @Delete
    fun deleteIncome(savedIncome: SavedIncome)
}