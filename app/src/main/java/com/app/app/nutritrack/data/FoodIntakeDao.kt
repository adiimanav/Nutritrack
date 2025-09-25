package com.app.app.nutritrack.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FoodIntakeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(foodIntake: FoodIntake)

    @Update
    suspend fun update(foodIntake: FoodIntake)

    @Delete
    suspend fun delete(foodIntake: FoodIntake)

    @Query("SELECT * FROM food_intake WHERE ResponderID = :userID")
    suspend fun getAllForUser(userID: String): List<FoodIntake>

    @Query("SELECT * FROM food_intake")
    suspend fun getAll(): List<FoodIntake>

}