package com.fit2081.aditya_33520070.nutritrack.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    val userID: String,
    val phoneNumber: String,
    val name: String,
    val sex: String,
    val HEIFATotalScore: Float?,
    val DiscretionaryHEIFAScore: Float?,
    val VegetablesHEIFAScore: Float?,
    val VegetablesVariationsScore: Float?,
    val FruitHEIFAScore: Float?,
    val FruitVariationsScore: Float?,
    val GrainsAndCerealsHEIFAScore: Float?,
    val WholeGrainsAndCerealsHEIFAScore: Float?,
    val MeatAndAlternativesHEIFAScore: Float?,
    val DairyAndAlternativesHEIFAScore: Float?,
    val SodiumHEIFAScore: Float?,
    val AlcoholHEIFAScore: Float?,
    val WaterHEIFAScore: Float?,
    val SugarHEIFAScore: Float?,
    val SaturatedFatHEIFAScore: Float?,
    val UnsaturatedFatHEIFAScore: Float?,
    val password: String? = null

)