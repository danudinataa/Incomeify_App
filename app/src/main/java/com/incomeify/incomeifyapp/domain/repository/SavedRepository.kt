package com.incomeify.incomeifyapp.domain.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.incomeify.incomeifyapp.data.local.SavedDatabase
import com.incomeify.incomeifyapp.data.local.SavedIncome
import com.incomeify.incomeifyapp.data.local.SavedIncomeDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class SavedRepository(private val savedIncomeDao: SavedIncomeDao, private val executor: ExecutorService) {

    companion object {

        @Volatile
        private var instance: SavedRepository? = null

        fun getInstance(context: Context): SavedRepository {
            return instance ?: synchronized(this) {
                if (instance == null) {
                    val database = SavedDatabase.getDatabase(context)
                    instance = SavedRepository(
                        database.savedIncomeDao(),
                        Executors.newSingleThreadExecutor()
                    )
                }
                return instance as SavedRepository
            }

        }
    }

    fun getIncomes(): LiveData<List<SavedIncome>> = savedIncomeDao.getIncomes()

    fun getIncomeById(savedId: Int): LiveData<SavedIncome> = savedIncomeDao.getIncomeById(savedId)

    fun insertIncome(newSavedIncome: SavedIncome) = executor.execute {
        savedIncomeDao.insertIncome(newSavedIncome)
    }

    fun deleteIncome(savedIncome: SavedIncome) {
        executor.execute {
            savedIncomeDao.deleteIncome(savedIncome)
        }
    }

}