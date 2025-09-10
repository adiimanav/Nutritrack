package com.fit2081.aditya_33520070.nutritrack.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FoodIntakeRepository {
    val foodIntakeDao: FoodIntakeDao

    // Constructor to initialize UserDao
    constructor(context: Context){
        foodIntakeDao = AppDatabase.getDatabase(context).foodIntakeDao()
    }

    // Function to insert a food intake record into the database.
    suspend fun insert(foodIntake: FoodIntake) {
        // Calls the insert function from the UserDao
        foodIntakeDao.insert(foodIntake)
    }

    // Function to delete a food intake record from the database
    suspend fun delete(foodIntake: FoodIntake) {
        // Calls the delete function from the UserDao
        foodIntakeDao.delete(foodIntake)
    }

    // Function to update a food intake record in the database.
    suspend fun update(foodIntake: FoodIntake) {
        // Call the update function from the UserDao
        foodIntakeDao.update(foodIntake)
    }

    // Function to get all food intake records of a user from the database
    suspend fun getAllForUser(userID: String): Flow<List<FoodIntake>> = flow{
        emit(foodIntakeDao.getAllForUser(userID))
    }

    // Function to get all food intake records from the database
    fun getAll(): Flow<List<FoodIntake>> = flow {
        emit(foodIntakeDao.getAll())
    }

}