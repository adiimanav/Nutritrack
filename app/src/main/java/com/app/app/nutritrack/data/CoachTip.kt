package com.fit2081.aditya_33520070.nutritrack.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NutriCoachTips")
data class CoachTip(
    @PrimaryKey(autoGenerate = true) val ID: Int = 0,
    val userID: String,
    val tip: String,
    val timeStamp: Long = System.currentTimeMillis()
)