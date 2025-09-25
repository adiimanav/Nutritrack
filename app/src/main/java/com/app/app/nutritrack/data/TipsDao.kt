package com.app.app.nutritrack.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TipsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTip(tip: CoachTip)

    @Query("SELECT * FROM NutriCoachTips WHERE userID = :userID")
    fun getAllTips(userID: String): LiveData<List<CoachTip>>
}