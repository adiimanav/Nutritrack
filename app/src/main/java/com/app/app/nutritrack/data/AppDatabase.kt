package com.fit2081.aditya_33520070.nutritrack.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class, FoodIntake::class, CoachTip::class], version = 1, exportSchema = false)

abstract class AppDatabase: RoomDatabase() {
    /**
     * Returns [UserDao] object
     */
    abstract fun UserDao(): UserDao

    /**
     * Returns [FoodIntakeDao] object
     */
    abstract fun foodIntakeDao(): FoodIntakeDao

    abstract fun TipsDao(): TipsDao

    companion object {
        /**
         * Volatile variable that holds the database instance
         * Volatile means that it will be immediately visible to all threads
         */
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase:: class.java, "app_database")
                    .build().also { Instance = it }
            }
        }
    }

}