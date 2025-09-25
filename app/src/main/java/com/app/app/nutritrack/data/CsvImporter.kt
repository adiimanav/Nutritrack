package com.app.app.nutritrack.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

object CsvImporter {
    suspend fun importData(context: Context, userRepository: UserRepository) {
        val prefs = context.getSharedPreferences("db_prefs", Context.MODE_PRIVATE)
        if (prefs.getBoolean("db_loaded", false)) return

        withContext(Dispatchers.IO) {
            val users = mutableListOf<User>()
            val inputStream = context.assets.open("Data.csv")
            val reader = BufferedReader(InputStreamReader(inputStream))
            val headerLine = reader.readLine() ?: return@withContext
            val headers = headerLine.split(",")

            reader.forEachLine { line ->
                val columns = line.split(",")
                if (columns.size < headers.size) return@forEachLine
                val sex = columns[2].trim()
                val checkGender = if (sex.equals("Male", ignoreCase = true)) "Male" else "Female"

                val user = User(
                    userID = columns[1].trim(),
                    phoneNumber = columns[0].trim(),
                    sex = sex,
                    name = "",
                    HEIFATotalScore = columns.getOrNull(headers.indexOf("HEIFAtotalscore$checkGender"))
                        ?.toFloatOrNull(),
                    DiscretionaryHEIFAScore = columns.getOrNull(headers.indexOf("DiscretionaryHEIFAscore$checkGender"))
                        ?.toFloatOrNull(),
                    VegetablesHEIFAScore = columns.getOrNull(headers.indexOf("VegetablesHEIFAscore$checkGender"))
                        ?.toFloatOrNull(),
                    VegetablesVariationsScore = columns.getOrNull(headers.indexOf("Vegetablesvariationsscore"))
                        ?.toFloatOrNull(),
                    FruitHEIFAScore = columns.getOrNull(headers.indexOf("FruitHEIFAscore$checkGender"))
                        ?.toFloatOrNull(),
                    FruitVariationsScore = columns.getOrNull(headers.indexOf("Fruitvariationsscore"))
                        ?.toFloatOrNull(),
                    GrainsAndCerealsHEIFAScore = columns.getOrNull(headers.indexOf("GrainsandcerealsHEIFAscore$checkGender"))
                        ?.toFloatOrNull(),
                    WholeGrainsAndCerealsHEIFAScore = columns.getOrNull(headers.indexOf("WholegrainsHEIFAscore$checkGender"))
                        ?.toFloatOrNull(),
                    MeatAndAlternativesHEIFAScore = columns.getOrNull(headers.indexOf("MeatandalternativesHEIFAscore$checkGender"))
                        ?.toFloatOrNull(),
                    DairyAndAlternativesHEIFAScore = columns.getOrNull(headers.indexOf("DairyandalternativesHEIFAscore$checkGender"))
                        ?.toFloatOrNull(),
                    SodiumHEIFAScore = columns.getOrNull(headers.indexOf("SodiumHEIFAscore$checkGender"))
                        ?.toFloatOrNull(),
                    AlcoholHEIFAScore = columns.getOrNull(headers.indexOf("AlcoholHEIFAscore$checkGender"))
                        ?.toFloatOrNull(),
                    WaterHEIFAScore = columns.getOrNull(headers.indexOf("WaterHEIFAscore$checkGender"))
                        ?.toFloatOrNull(),
                    SugarHEIFAScore = columns.getOrNull(headers.indexOf("SugarHEIFAscore$checkGender"))
                        ?.toFloatOrNull(),
                    SaturatedFatHEIFAScore = columns.getOrNull(headers.indexOf("SaturatedFatHEIFAscore$checkGender"))
                        ?.toFloatOrNull(),
                    UnsaturatedFatHEIFAScore = columns.getOrNull(headers.indexOf("UnsaturatedFatHEIFAscore$checkGender"))
                        ?.toFloatOrNull(),
                    password = null
                )
                users.add(user)
            }
            users.forEach { userRepository.insert(it) }
            prefs.edit().putBoolean("db_loaded", true).apply()
        }
    }
}
