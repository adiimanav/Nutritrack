package com.app.app.nutritrack

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.app.nutritrack.ui.theme.NutritrackTheme
import kotlin.jvm.java
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.app.nutritrack.data.CoachTipViewModel
import com.app.app.nutritrack.data.FruitsRepository
import com.app.app.nutritrack.data.GenAIState
import com.app.app.nutritrack.data.GenAIViewModel
import com.fit2081.aditya_33520070.nutritrack.R
import com.app.app.nutritrack.data.User
import com.app.app.nutritrack.data.UserViewModel
import com.app.app.nutritrack.data.network.FruityViceResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch




class ScoreActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NutritrackTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {MyBottombar(navController)}
                ) { innerPadding ->
                    MyNavHost(
                        innerPadding = innerPadding, navController = navController
                    )
                }
            }
        }
    }
}



@Composable
fun HomeScreen(modifier: Modifier = Modifier){

    val context = LocalContext.current
    val userViewModel: UserViewModel = viewModel(factory = UserViewModel.UserViewModelFactory(context))

    val currentUserId = context.getSharedPreferences("current_user_details", Context.MODE_PRIVATE)
        .getString("selectedUserId", "").toString()

    var user by remember { mutableStateOf<User?>(null) }

    LaunchedEffect(currentUserId) {
        user = userViewModel.getUserByID(currentUserId)
    }
    Log.d("ADI",currentUserId)


    Column(

        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ){
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Hello,",
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF808080),
            textAlign = TextAlign.Left
        )

        Text(
            text = "User $currentUserId",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier.fillMaxWidth().height(80.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "You've already filled in your Food Intake Questionnaire, but you can " +
                        "change the details here:",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Justify,
                modifier = Modifier.weight(1f),
            )
            Button(
                onClick = {
                    val intent = Intent(context, QuestionnaireActivity::class.java)
                    context.startActivity(intent)

                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF013220)),
                modifier = Modifier.padding(24.dp),
                shape = MaterialTheme.shapes.small,


                ) {
                Text("✐ Edit")
            }

        }
        Image(painter = painterResource(id = R.drawable.foodplate),
            contentDescription = "Food plate",
            modifier = Modifier.size(350.dp),
            contentScale = ContentScale.Crop
            )

        Row(
            modifier = Modifier.fillMaxWidth().height(80.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "My Score",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold)

            Text(
                text = "See all scores >",
                color = Color(0xFF808080),
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Your Food Quality score",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = "${user?.HEIFATotalScore}/100",
                color = Color(0xFF6200EE),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )


        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "What is the Food Quality Score?",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
            )
        Text(
            text = "Your Food Quality Score provides a snapshot of how well your eating patterns " +
                    "align with established food guidelines, helping you identify both strenghts " +
                    "and opportunities for improvement in your diet.\n This personalized measurement " +
                    "considers various food groups including vegetables, fruits, whole grains, and " +
                    "proteins to give you practical insights for making healthier food choices.",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Justify
        )
        Spacer(modifier = Modifier.height(45.dp))



        }
    }

@SuppressLint("DefaultLocale")
@Composable
fun InsightsScreen(navHostController: NavHostController) {
    val context = LocalContext.current

    val currentUserId = context.getSharedPreferences("current_user_details", Context.MODE_PRIVATE)
        .getString("selectedUserId", "").toString()

    val userViewModel: UserViewModel = viewModel(factory = UserViewModel.UserViewModelFactory(context))
    var user by remember { mutableStateOf<User?>(null) }

    LaunchedEffect(currentUserId) {
        user = userViewModel.getUserByID(currentUserId)
    }


    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {

            Text(
                text = "Insights: Food Score",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

        ScoreSliderRow("Vegetables", user?.VegetablesHEIFAScore)
        ScoreSliderRow("Fruits", user?.FruitHEIFAScore)
        ScoreSliderRow("Cereals", user?.GrainsAndCerealsHEIFAScore)
        ScoreSliderRow("Whole Grains", user?.WholeGrainsAndCerealsHEIFAScore)
        ScoreSliderRow("Meat Alternatives", user?.MeatAndAlternativesHEIFAScore)
        ScoreSliderRow("Dairy", user?.DairyAndAlternativesHEIFAScore)
        ScoreSliderRow("Water", user?.WaterHEIFAScore, 5f)
        ScoreSliderRow("Unsaturated Fats", user?.UnsaturatedFatHEIFAScore)
        ScoreSliderRow("Sodium", user?.SodiumHEIFAScore)
        ScoreSliderRow("Sugar", user?.SugarHEIFAScore)
        ScoreSliderRow("Alcohol", user?.AlcoholHEIFAScore, 5f)
        ScoreSliderRow("Discretionary", user?.DiscretionaryHEIFAScore)
        ScoreSliderRow("Saturated Fats", user?.SaturatedFatHEIFAScore)

        Spacer(modifier = Modifier.height(8.dp))

        ScoreSliderRow("Total Food Score: ", user?.HEIFATotalScore, 100f)


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Button(
                onClick = {
                    val shareIntent = Intent(ACTION_SEND)
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Here's my Food Quality Score: ${String.format("%.2f/100", user?.HEIFATotalScore)}")
                    context.startActivity(Intent.createChooser(shareIntent, "Share score via:"))
                }
            ) {Text("Share Food Score") }
            Button(
                onClick = {
                    navHostController.navigate("NutriCoach")

                }
            ){Text("Improve your Diet!")}
        }

        }






}

@SuppressLint("DefaultLocale")
@Composable
fun ScoreSliderRow(label: String, value: Float?, max: Float = 10f) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label ",
            fontWeight = FontWeight.SemiBold,
        )
        Slider(
            value = value ?: 0f,
            onValueChange = {},
            valueRange = 0f..max,
            enabled = false,
            modifier = Modifier.weight(1.5f)
        )
        Text(
            text = String.format("%.2f/%.0f", value ?: 0f, max),
            fontSize = 15.sp,
            modifier = Modifier.weight(1f)
        )
    }
}



@Composable
fun NutriCoachScreen() {
    var fruitName by remember { mutableStateOf("") }
    var fruitDetails by remember { mutableStateOf<FruityViceResponseModel?>(null) }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    val repository = remember { FruitsRepository() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val genAIViewModel: GenAIViewModel = viewModel(factory = GenAIViewModel.GenAIViewModelFactory(context))
    val genAIState by genAIViewModel.genAIState.collectAsState()
    var showTipsDialog by remember { mutableStateOf(false) }

    val coachTipViewModel: CoachTipViewModel = viewModel(factory = CoachTipViewModel.CoachTipViewModelFactory(context))

    val currentUserId = context.getSharedPreferences("current_user_details", Context.MODE_PRIVATE)
        .getString("selectedUserId", "") ?: ""



    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize()) {

        Text(
            text = "NutriCoach",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Find the nutritional information of a fruit:",
            style = MaterialTheme.typography.bodyLarge)

        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = fruitName,
                onValueChange = { fruitName = it },
                label = { Text("Fruit Name") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    loading = true
                    error = null
                    fruitDetails = null
                    coroutineScope.launch(Dispatchers.IO) {
                        val response = try {
                            repository.getDetails(fruitName)
                        } catch (e: Exception) {
                            null
                        }
                        loading = false
                        if (response != null) {
                            fruitDetails = response
                        } else {
                            error = "Fruit not found."
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF013220))
            ) {
                Text("Details")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Press on the Details button to find out.")

        when {
            loading -> CircularProgressIndicator()
            error != null -> Text(error ?: "Error", color = MaterialTheme.colorScheme.error)
            fruitDetails != null -> FruitDetailsCard(fruitDetails!!)
        }

        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider(thickness = 2.dp)
        Spacer(modifier = Modifier.height(16.dp))


        Text("Motivational Message (AI)", style = MaterialTheme.typography.titleMedium)

        Button(
            onClick = { genAIViewModel.sendPrompt(context) },
            modifier = Modifier.padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF013220))
        ) {
            Text("Get Motivational Message")
        }

        when (genAIState) {
            is GenAIState.Loading -> CircularProgressIndicator()
            is GenAIState.Success -> Text(
                (genAIState as GenAIState.Success).outputText,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            is GenAIState.Error -> Text(
                (genAIState as GenAIState.Error).errorMessage,
                color = MaterialTheme.colorScheme.error
            )
            else -> {}
        }

        Spacer(modifier = Modifier.height(128.dp))
        Button(
            onClick = { showTipsDialog = true },
            modifier = Modifier.padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF013220))
        ) {
            Text("Show All Tips")
        }

//        if (showTipsDialog) {
//            val tips = coachTipViewModel.getAllTips(currentUserId).observeAsState(emptyList())
//
//            AlertDialog(
//                onDismissRequest = { showTipsDialog = false },
//                title = { Text("Your Previous Tips") },
//                text = {
//                    Column {
//                        tips.value.forEach { tip ->
//                            Text("- ${tip.tip}", style = MaterialTheme.typography.bodyMedium)
//                        }
//                    }
//                },
//                confirmButton = {
//                    Button(onClick = { showTipsDialog = false }) {
//                        Text("Close")
//                    }
//                }
//            )
//        }



    }
}

@Composable
fun SettingsScreen(navHostController: NavHostController) {
    val context = LocalContext.current

    val currentUserId = context.getSharedPreferences("current_user_details", Context.MODE_PRIVATE)
        .getString("selectedUserId", "").toString()

    val userViewModel: UserViewModel = viewModel(factory = UserViewModel.UserViewModelFactory(context))
    var user by remember { mutableStateOf<User?>(null) }
    LaunchedEffect(currentUserId) {
        user = userViewModel.getUserByID(currentUserId)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),

    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        // User Details
        Text("ACCOUNT", style = MaterialTheme.typography.labelMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Person, contentDescription = "User")
            Spacer(Modifier.width(8.dp))
            Text(user?.name.toString())
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Phone, contentDescription = "Phone")
            Spacer(Modifier.width(8.dp))
            Text(user?.phoneNumber.toString())
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Lock, contentDescription = "ID")
            Spacer(Modifier.width(8.dp))
            Text(user?.userID.toString())
        }

        Spacer(modifier = Modifier.height(32.dp))
        HorizontalDivider(thickness = 2.dp)

        // Other Settings
        Spacer(modifier = Modifier.height(48.dp))
        Text("OTHER SETTINGS", style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground)
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF013220))
        ) {
            Icon(Icons.Default.ExitToApp, contentDescription = "Logout")
            Spacer(Modifier.width(8.dp))
            Text("Logout")
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navHostController.navigate("clinician_login") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA020F0))
        ) {
            Icon(Icons.Default.Add, contentDescription = "Clinician Login")
            Spacer(Modifier.width(8.dp))
            Text("Clinician Login")
        }
    }
}



@Composable
fun FruitDetailsCard(fruit: FruityViceResponseModel?) {
    Column {
        InfoRow("family", fruit?.family ?: "")
        InfoRow("calories", fruit?.nutritions?.calories.toString())
        InfoRow("fat", fruit?.nutritions?.fat.toString())
        InfoRow("sugar", fruit?.nutritions?.sugar.toString())
        InfoRow("carbohydrates", fruit?.nutritions?.carbohydrates.toString())
        InfoRow("protein", fruit?.nutritions?.protein.toString())
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("$label :", modifier = Modifier.weight(1f))
        Text(value, modifier = Modifier.weight(1f))
    }
}


@Composable
fun MyBottombar(navHostController: NavHostController){
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Home", "Insights", "NutriCoach", "Settings")

    NavigationBar {
        val items = listOf(
            "Home",
            "Insights",
            "NutriCoach",
            "Settings"
        )
        items.forEachIndexed {index, item ->
            NavigationBarItem(
                icon = {
                    when (item) {
                        "Home" -> Icon(Icons.Filled.Home, contentDescription = "Home")
                        "Insights" -> Icon(Icons.Filled.MoreVert, contentDescription = "Insights")
                        "NutriCoach" -> Icon(Icons.Filled.Face, contentDescription = "NutriCoach")
                        "Settings" -> Icon(Icons.Filled.Settings, contentDescription = "Settings")
                    }
                },
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navHostController.navigate(item)
                }
            )
        }
    }

}

@Composable
fun MyNavHost(navController: NavHostController, innerPadding: PaddingValues){
    NavHost(

        navController = navController,

        startDestination = "Home"
    ){
        composable("Home") {
            HomeScreen()
        }
        composable("Insights") {
            InsightsScreen(navController)
        }
        composable("NutriCoach"){
            NutriCoachScreen()
        }
        composable("Settings") {
            SettingsScreen(navController)
        }
    }
}
