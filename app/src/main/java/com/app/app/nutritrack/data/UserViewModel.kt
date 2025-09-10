package com.fit2081.aditya_33520070.nutritrack.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UserViewModel(context: Context) : ViewModel() {

    // Create a repository object that will be used to interact with the database
    private val userRepository = UserRepository(context)
    // get all users
    val allUsers: Flow<List<User>> = userRepository.getAllUsers()

    // insert user into the repository
    fun insert(user: User) = viewModelScope.launch {
        userRepository.insert(user)
    }

    // delete user from the repository
    fun delete(user: User) = viewModelScope.launch {
        userRepository.delete(user)
    }

    // delete all users from the repository
    fun deleteAllUsers() = viewModelScope.launch {
        userRepository.deleteAllUsers()
    }

    suspend fun getUserByID(userID: String): User? =
        userRepository.getUserByID(userID)

    suspend fun getUserByIDAndPassword(userID: String, password: String): User? =
        userRepository.getUserByIDAndPassword(userID, password)

    suspend fun update(user: User) = userRepository.update(user)

    // A View Model factory that sets the context for the viewmodel
    // provides an interface that is used to create view models
    class UserViewModelFactory(context: Context) : ViewModelProvider.Factory {
        private val context = context.applicationContext

        override fun<T : ViewModel> create(modelClass: Class<T>): T =
            UserViewModel(context) as T
    }

}
