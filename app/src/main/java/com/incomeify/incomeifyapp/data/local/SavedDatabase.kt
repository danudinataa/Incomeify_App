package com.incomeify.incomeifyapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [SavedIncome::class],
    version = 1,
    exportSchema = false
)

abstract class SavedDatabase : RoomDatabase() {

    abstract fun savedIncomeDao():SavedIncomeDao

    companion object {
        @Volatile
        private var INSTANCE : SavedDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): SavedDatabase {
            if (INSTANCE == null) {
                synchronized(SavedDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        SavedDatabase::class.java, "saved_income")
                        .build()
                }
            }
            return INSTANCE as SavedDatabase
        }
    }
}