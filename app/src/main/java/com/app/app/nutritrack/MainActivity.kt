package com.fit2081.aditya_33520070.nutritrack

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.fit2081.aditya_33520070.nutritrack.data.CsvImporter
import com.fit2081.aditya_33520070.nutritrack.data.UserRepository
import com.fit2081.aditya_33520070.nutritrack.ui.theme.NutritrackTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userRepository = UserRepository(applicationContext)
        lifecycleScope.launch {
            CsvImporter.importData(applicationContext, userRepository)
        }
        enableEdgeToEdge()
        setContent {
            NutritrackTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WelcomeScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun WelcomeScreen(modifier: Modifier = Modifier){
    val context = LocalContext.current

    Surface(modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background)
    {
        Column(
            modifier = modifier.padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally

        ){
        // App Title
        Text("NutriTrack",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )


        // App Logo
        Image(
            painter = painterResource(id = R.drawable.nutritrack_logo_removebg_preview),
            contentDescription = "Nutritrack logo",
            modifier = Modifier.size(360.dp),
            contentScale = ContentScale.Crop
        )



        Text(
            text = "This app provides general health and nutrition information for\n" +
                    "educational purposes only. It is not intended as medical advice,\n" +
                    "diagnosis, or treatment. Always consult a qualified healthcare\n" +
                    "professional before making any changes to your diet, exercise, or\n" +
                    "health regimen.\n" +
                    "Use this app at your own risk.\n" +
                    "If you’d like to an Accredited Practicing Dietitian (APD), please\n" +
                    "visit the Monash Nutrition/Dietetics Clinic (discounted rates for\n" +
                    "students):https://www.monash.edu/medicine/scs/nutrition/clinics/nutrition",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 2.dp)
        )



        Button(
            onClick = {
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF013220)),
            modifier = Modifier.fillMaxWidth(0.8f)
            ) { Text("Login", color = Color.White) }
        Spacer(modifier = Modifier.height(36.dp))
        Text(text = "Made by Aditya Ansh (33520070)")

        }

    }
}