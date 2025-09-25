package com.app.app.nutritrack.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    // Insert a new user into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    // Update existing user in the database
    @Update
    suspend fun update(user: User)

    // Delete a specific user from the database
    @Delete
    suspend fun delete(user: User)

    // Delete all patients from the database
    @Query("DELETE FROM user")
    suspend fun deleteAllUsers()

    // Delete user by ID
    @Query("DELETE FROM user WHERE userID = :userID")
    suspend fun deleteUserByID(userID: Int)

    //Retrieve all users from the database, ordered by their ID in ascending order
    @Query("SELECT * FROM user ORDER BY userID ASC")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM user WHERE userID = :userID")
    suspend fun getUserByID(userID: String): User?

    @Query("SELECT * FROM user WHERE userID = :userID AND password = :password")
    suspend fun getUserByIDAndPassword(userID: String, password: String): User?
}