package com.app.app.nutritrack.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FoodIntakeViewModel(context: Context): ViewModel() {

    // Create a repository object that will be used to interact with the database
    private val foodIntakeRepository = FoodIntakeRepository(context)
    // get all responses
    val allResponses: Flow<List<FoodIntake>> = foodIntakeRepository.getAll()

    // insert response into the repository
    fun insert(foodIntake: FoodIntake) = viewModelScope.launch {
        foodIntakeRepository.insert(foodIntake)
    }

    // delete response from the repository
    fun delete(foodIntake: FoodIntake) = viewModelScope.launch {
        foodIntakeRepository.delete(foodIntake)
    }

    fun getAllForUser(userID: String, onLoaded: (FoodIntake?) -> Unit) {
        viewModelScope.launch {
            val all = foodIntakeRepository.getAllForUser(userID)
            all.collect { responses ->
                val latest = responses.lastOrNull()
                onLoaded(latest)
            }
        }
    }

    // A View Model factory that sets the context for the viewmodel
    // provides an interface that is used to create view models
    class FoodIntakeViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun<T : ViewModel> create(modelClass: Class<T>): T =
            FoodIntakeViewModel(context) as T
    }



}