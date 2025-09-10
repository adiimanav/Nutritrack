package com.fit2081.aditya_33520070.nutritrack.data

import android.content.Context

class NutriCoachRepository(val context: Context) {
    val database = AppDatabase.getDatabase(context)
    val userDao = database.UserDao()

    fun getCurrentUserID(): String {
        return context.getSharedPreferences("current_user_details", Context.MODE_PRIVATE)
            .getString("selectedUserId", "").toString()

    }

    suspend fun getUserByID(userID: String): User? {
        return userDao.getUserByID(userID)
    }

    suspend fun getAllUsers() = userDao.getAllUsers()
}