package com.app.app.nutritrack.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * ViewModel class for handling interactions with a generative AI model.
 * This class manages the UI state and communicates with the GenerativeModel
 * to generate content based on user prompts.
 */
class GenAIViewModel(context: Context): ViewModel() {

    /**
     * Mutable state flow to hold the current UI state
     */

    private val _genAIState: MutableStateFlow<GenAIState> =
        MutableStateFlow(GenAIState.Initial)

    /**
     * Publicly exposed immutable state flow for observing the UI state.
     */
    val genAIState: StateFlow<GenAIState> =
        _genAIState.asStateFlow()

    /**
     * Instance of GenerativeModel used to generate content
     */
    private val generativeModel = GenerativeModel(
    modelName = "gemini-1.5-flash",
        apiKey=<Insert your API Key>
    )

    private val _tips = MutableStateFlow<List<CoachTip>>(emptyList())
    val tips = _tips

    fun getAllTips(context: Context, user: User): LiveData<List<CoachTip>> {
        val db = AppDatabase.Companion.getDatabase(context)
        return db.TipsDao().getAllTips(user.userID) // This must return LiveData<List<CoachTip>>
    }


    fun sendPrompt(context: Context) {
        _genAIState.value = GenAIState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            val currentUserId = context.getSharedPreferences("current_user_details", Context.MODE_PRIVATE)
                .getString("selectedUserId", "")

            val foodIntakeRepository = FoodIntakeRepository(context)
            val nutriCoachRepository = NutriCoachRepository(context)
            val user = nutriCoachRepository.getUserByID(currentUserId.toString())
            val foodIntake = foodIntakeRepository.getAllForUser(currentUserId.toString())

            val craftedPrompt = buildString {
                append("Create a short message to encourage user to improve their fruit intake. Personalize it according to details below. ")

                append("Information about this user: ${user?.name} \n")
                append("Fruit score: ${user?.FruitHEIFAScore}")
                append("Vegetables: ${user?.VegetablesHEIFAScore}, Fruits: ${user?.FruitHEIFAScore}, Grains: ${user?.GrainsAndCerealsHEIFAScore}," +
                        "Whole Grains: ${user?.WholeGrainsAndCerealsHEIFAScore}, Meat: ${user?.MeatAndAlternativesHEIFAScore}, Dairy: ${user?.DairyAndAlternativesHEIFAScore}," +
                        "Water: ${user?.WaterHEIFAScore}, Alcohol: ${user?.AlcoholHEIFAScore}")
            }
            val response = generativeModel.generateContent(content {text(craftedPrompt)}).text

            if(response != null) {
                saveTip(
                    currentUserId.toString(), response,
                    context = context
                )
                _genAIState.value = GenAIState.Success(response)
            }
            else {
                _genAIState.value = GenAIState.Error("No response.")
            }





        }


    }

    fun saveTip(userID: String, tip: String, context: Context){
        val tip = CoachTip(userID = userID, tip = tip)
        val appDataBase = AppDatabase.Companion.getDatabase(context)
        viewModelScope.launch(Dispatchers.IO) {
            appDataBase.TipsDao().insertTip(tip)
        }
    }


    class GenAIViewModelFactory(val context: Context): ViewModelProvider.Factory {
        override fun <T: ViewModel> create(modelClass: Class<T>): T {
            return GenAIViewModel(context) as T
        }
    }





}