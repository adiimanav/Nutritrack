package com.fit2081.aditya_33520070.nutritrack.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "food_intake",
    foreignKeys = [ForeignKey(entity = User::class,
        parentColumns = ["userID"],
        childColumns = ["ResponderID"],
        onDelete = ForeignKey.CASCADE)])

data class FoodIntake(
    @PrimaryKey(autoGenerate = true)
    val ID: Int = 0,
    val ResponderID: String,
    val foodChoices: String,
    val persona: String,
    val biggestMealTime: String,
    val sleepTime: String,
    val wakeTime: String

)