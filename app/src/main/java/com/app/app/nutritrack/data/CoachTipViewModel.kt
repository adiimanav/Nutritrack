package com.fit2081.aditya_33520070.nutritrack.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers

class CoachTipViewModel(private val context: Context) : ViewModel() {

    private val db = AppDatabase.getDatabase(context)
    private val tipsDao = db.TipsDao()

    fun getAllTips(userId: String) = liveData(Dispatchers.IO) {
        emit(tipsDao.getAllTips(userId))
    }

    class CoachTipViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CoachTipViewModel::class.java)) {
                return CoachTipViewModel(context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
