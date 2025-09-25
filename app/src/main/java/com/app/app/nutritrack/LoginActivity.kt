package com.app.app.nutritrack

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.app.nutritrack.data.UserViewModel
import com.app.app.nutritrack.ui.theme.NutritrackTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NutritrackTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(
                        modifier = Modifier.padding(innerPadding)

                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val userViewModel: UserViewModel = viewModel(factory = UserViewModel.UserViewModelFactory(context))
    val users by userViewModel.allUsers.collectAsState(initial = emptyList())

    var selectedUserId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isExpanded by remember { mutableStateOf(false) }
    var loginError by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    Column(modifier = modifier.padding(24.dp)) {
        Spacer(modifier = Modifier.height(32.dp))
        Text("Log in", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        // User ID dropdown
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = it }
        ) {
            OutlinedTextField(
                value = selectedUserId,
                onValueChange = {},
                label = { Text("My ID (Provided by your Clinician)") },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                users.forEach { user ->
                    DropdownMenuItem(
                        text = { Text(user.userID) },
                        onClick = {
                            selectedUserId = user.userID
                            isExpanded = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        // Password input
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "This app is only for pre-registered users. Please enter your ID and password or Register to claim your account on your first visit.",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Continue button
        Button(
            onClick = {
                isLoading = true
                loginError = null
                CoroutineScope(Dispatchers.Main).launch {
                    val user = userViewModel.getUserByIDAndPassword(selectedUserId, password)
                    isLoading = false
                    if (user != null) {
                        // Save login state
                        context.getSharedPreferences("current_user_details", Context.MODE_PRIVATE)
                            .edit().putString("selectedUserId", selectedUserId).apply()
                        // Navigate to Questionnaire
                        context.startActivity(Intent(context, QuestionnaireActivity::class.java))
                    } else {
                        loginError = "Invalid ID or password."
                    }
                }
            },
            enabled = selectedUserId.isNotEmpty() && password.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF013220))
        ) {
            Text("Continue")
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Register button
        Button(
            onClick = {
                // Navigate to Registration Screen
                context.startActivity(Intent(context, RegisterActivity::class.java))
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF013220))
        ) {
            Text("Register")
        }

        if (loginError != null) {
            Text(loginError!!, color = MaterialTheme.colorScheme.error)
        }
    }
}

