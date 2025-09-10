package com.fit2081.aditya_33520070.nutritrack.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NutriCoachViewModel(val context: Context): ViewModel() {
    val NutriCoachRepository = NutriCoachRepository(context)

    val _userID = MutableLiveData<String>()
    val userID: LiveData<String> = _userID

    val _totalScore = MutableLiveData<Float>()
    val totalScore: LiveData<Float> = _totalScore

    val _sex = MutableLiveData<String>()
    val sex: LiveData<String> = _sex

    val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    fun getUserData() {
        val currentUser = NutriCoachRepository.getCurrentUserID()
        _userID.value = currentUser

        viewModelScope.launch(Dispatchers.IO) {
            val user: User? = NutriCoachRepository.getUserByID(currentUser)
            if (user != null) {
                _sex.value = user.sex
                _name.value = user.name
                _totalScore.value = user.HEIFATotalScore ?: 0f
            }
        }

        class NutriCoachViewModelFactory(context: Context) : ViewModelProvider.Factory {
            private val context = context.applicationContext

            override fun<T : ViewModel> create(modelClass: Class<T>): T =
                NutriCoachViewModel(context) as T
        }
    }
}