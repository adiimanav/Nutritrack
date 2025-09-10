package com.fit2081.aditya_33520070.nutritrack.data

import android.content.Context
import kotlinx.coroutines.flow.Flow

class UserRepository {

    var userDao: UserDao

    // Constructor to initialize UserDao
    constructor(context: Context){
        userDao = AppDatabase.getDatabase(context).UserDao()
    }

    // Function to insert a user into the database.
    suspend fun insert(user: User) {
        // Calls the insert function from the UserDao
        userDao.insert(user)
    }

    // Function to delete a user from the database
    suspend fun delete(user: User) {
        // Calls the delete function from the UserDao
        userDao.delete(user)
    }

    // Function to update a user in the database.
    suspend fun update(user: User) {
        // Call the update function from the UserDao
        userDao.update(user)
    }

    // Function to delete all users from the database
    suspend fun deleteAllUsers() {
        userDao.deleteAllUsers()
    }

    // Function to get all users from the database
    fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()

    suspend fun getUserByID(userID: String): User? = userDao.getUserByID(userID)

    suspend fun getUserByIDAndPassword(userID: String, password: String): User? = userDao.getUserByIDAndPassword(userID, password)


}