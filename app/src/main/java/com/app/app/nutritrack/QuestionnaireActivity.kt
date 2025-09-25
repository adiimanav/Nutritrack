package com.app.app.nutritrack

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fit2081.aditya_33520070.nutritrack.R
import com.app.app.nutritrack.data.FoodIntake
import com.app.app.nutritrack.data.FoodIntakeViewModel
import com.app.app.nutritrack.ui.theme.NutritrackTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class QuestionnaireActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NutritrackTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuestionnaireScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@SuppressLint("CommitPrefEdits")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionnaireScreen(modifier: Modifier) {
    val context = LocalContext.current

    val currentUserId = context.getSharedPreferences("current_user_details", Context.MODE_PRIVATE)
        .getString("selectedUserId", "")




    val checkBoxStates = remember { mutableStateListOf(
        false,false,false,
        false,false,false,
        false,false,false,) }

    val foodIntakeViewModel: FoodIntakeViewModel = viewModel(factory = FoodIntakeViewModel.FoodIntakeViewModelFactory(context))

    val foodChoices = listOf(
        "Fruits", "Vegetables", "Grains",
        "Red Meat", "Seafood", "Poultry",
        "Fish", "Eggs", "Nuts/Seeds")

    val personaChoices = listOf(
        "Health Devotee", "Mindful Eater", "Wellness Striver",
        "Balance Seeker", "Health Procrastinator", "Food Carefree"
    )


    var isExpanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var selectedPersona by remember { mutableStateOf("") }

    val biggestMealTime = remember { mutableStateOf("00:00") }
    val sleepTime = remember { mutableStateOf("00:00") }
    val wakeTime = remember { mutableStateOf("00:00") }

    val showMealTimePicker = timePicker(biggestMealTime)
    var showSleepTimePicker = timePicker(sleepTime)
    var showWakeTimePicker = timePicker(wakeTime)

    LaunchedEffect(Unit) {

        currentUserId?.let { userID ->
            foodIntakeViewModel.getAllForUser(userID) { intake ->
                intake?.let {
                    val selectedFoods = it.foodChoices.split(", ").map { it.trim() }
                    foodChoices.forEachIndexed { index, label ->
                        checkBoxStates[index] = selectedFoods.contains(label)
                    }
                    selectedPersona = it.persona
                    biggestMealTime.value = it.biggestMealTime
                    sleepTime.value = it.sleepTime
                    wakeTime.value = it.wakeTime
                }
            }
        }

    }

    val personaDetails = mapOf(
        "Health Devotee" to PersonaDetail(
            imageRes = R.drawable.persona_1,  // Replace with your image resource
            description = "I’m passionate about healthy eating & health plays a big part in my life. " +
                    "I use social media to follow active lifestyle personalities or get new recipes/exercise ideas. " +
                    "I may even buy superfoods or follow a particular type of diet. I like to think I am super healthy."
        ),
        "Mindful Eater" to PersonaDetail(
            imageRes = R.drawable.persona_2,  // Replace with your image resource
            description = "I’m health-conscious and being healthy and eating healthy is important to me. " +
                    "Although health means different things to different people, " +
                    "I make conscious lifestyle decisions about eating based on what I believe healthy " +
                    "means. I look for new recipes and healthy eating information on social media."
        ),
        "Wellness Striver" to PersonaDetail(
            imageRes = R.drawable.persona_3,  // Replace with your image resource
            description = "I aspire to be healthy (but struggle sometimes). " +
                    "Healthy eating is hard work! I’ve tried to improve my diet, " +
                    "but always find things that make it difficult to stick with the changes. " +
                    "Sometimes I notice recipe ideas or healthy eating hacks, " +
                    "and if it seems easy enough, I’ll give it a go."
        ),
        "Balance Seeker" to PersonaDetail(
            imageRes = R.drawable.persona_4,  // Replace with your image resource
            description = "I try and live a balanced lifestyle, and I think that all foods are okay " +
                    "in moderation. I shouldn’t have to feel guilty about eating a piece of cake now " +
                    "and again. I get all sorts of inspiration from social media like finding out about " +
                    "new restaurants, fun recipes and sometimes healthy eating tips."
        ),
        "Health Procrastinator" to PersonaDetail(
            imageRes = R.drawable.persona_5,  // Replace with your image resource
            description = "I’m contemplating healthy eating but it’s not a priority for me right now. " +
                    "I know the basics about what it means to be healthy, but it doesn’t seem relevant " +
                    "to me right now. I have taken a few steps to be healthier but I am not motivated " +
                    "to make it a high priority because I have too many other things going on in my life."
        ),
        "Food Carefree" to PersonaDetail(
            imageRes = R.drawable.persona_6,  // Replace with your image resource
            description = "I’m not bothered about healthy eating. I don’t really see the point and I don’t " +
                    "think about it. I don’t really notice healthy eating tips or recipes and I don’t care " +
                    "what I eat."
        )
    )


    Column(
            modifier = Modifier.fillMaxWidth(),

        ){
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "Food Intake Questionnaire",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(48.dp))
        HorizontalDivider(
            thickness = 2.dp,
        )


        Text(
            text = "Tick all the food categories you can eat",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(16.dp)
        )

        foodChoices.chunked(3).forEach { items ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                items.forEach { foodItem ->
                    val index = foodChoices.indexOf(foodItem)
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Checkbox(
                            checked = checkBoxStates[index],
                            onCheckedChange = { checkBoxStates[index] = it }
                        )
                        Text(foodItem)
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(
            thickness = 2.dp,
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Your Persona",

                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Left
            )
            Text(
                text = "People can be broadly classified into 6 different types based on their eating preferences." +
                        " Click on each button below to find out the different types, and select the type that best fits you!",

                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Justify,
                fontSize = 15.sp
            )
        }
        Spacer(modifier = Modifier.height(2.dp))

        personaChoices.chunked(3).forEach { personas ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                personas.forEach { persona ->
                    Button(
                        onClick = {
                            selectedPersona = persona
                            showDialog = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF013220)),
                        modifier = Modifier.weight(1f),
                        shape = MaterialTheme.shapes.small,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = persona,
                            style = MaterialTheme.typography.labelLarge,
                            textAlign = TextAlign.Center
                            )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp)) // spacing between rows
        }
        Spacer(modifier = Modifier.height(2.dp))
        HorizontalDivider(thickness = 2.dp)

        Spacer(modifier = Modifier.height(12.dp))
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = {isExpanded = it}
        ) {
            OutlinedTextField(
                value = selectedPersona,
                onValueChange = {},
                label = { Text("Which persona best fits you?") },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = {isExpanded = false}
            ) {
                personaChoices.forEach { persona ->
                    DropdownMenuItem(
                        text = {Text(persona)},
                        onClick = {
                            selectedPersona = persona
                            isExpanded = false
                        })
                }}
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "Timings",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Left
        )
        // Meal Time picker
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "At what time do you usally eat your biggest meal?",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.weight(1f)
            )
            OutlinedButton(
                onClick = { showMealTimePicker.show() }
            ) { Text(text = biggestMealTime.value)}
        }
        // Sleep time picker
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "At what time do you usally go to sleep?",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.weight(1f)
            )
            OutlinedButton(
                onClick = { showSleepTimePicker.show()}
            ) { Text(text = sleepTime.value)}
        }
        // Wake Time picker
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "At what time do you usally wake up?",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.weight(1f)
            )
            OutlinedButton(
                onClick = { showWakeTimePicker.show()}
            ) { Text(text = wakeTime.value)}
        }
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {

                CoroutineScope(Dispatchers.IO).launch {
                    val foodChoicesString = foodChoices
                        .filterIndexed { index, _ -> checkBoxStates[index] }
                        .joinToString(", ")

                    val intake = FoodIntake(
                        ResponderID = currentUserId ?: "",
                        foodChoices = foodChoicesString,
                        persona = selectedPersona,
                        biggestMealTime = biggestMealTime.value,
                        sleepTime = sleepTime.value,
                        wakeTime = wakeTime.value
                    )
                    foodIntakeViewModel.insert(intake)
                }
                Toast.makeText(context, "Saved successfully!", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, ScoreActivity::class.java)
                context.startActivity(intent)

            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF013220)),
            modifier = Modifier.padding(24.dp)
                .align(Alignment.CenterHorizontally),
            shape = MaterialTheme.shapes.small,


        ) {
            Text("Save")
        }

    }




    // For Modals
    if (showDialog){
        val personaDetail = personaDetails[selectedPersona]
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = selectedPersona,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )},
            text = {
                Column(
                    modifier = Modifier.padding(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Image(
                        painter = painterResource(id = personaDetail?.imageRes ?: R.drawable.nutritrack_logo_removebg_preview),
                        contentDescription = selectedPersona,
                        modifier = Modifier.size(360.dp),
                        alignment = Alignment.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = personaDetail?.description ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Justify
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
            },
            confirmButton = {
                Button(
                    onClick = { showDialog = false }
                ){
                    Text("Dismiss")
                }
            }


        )
    }
    }

// Data class for storing persona details
data class PersonaDetail(val imageRes: Int, val description: String)

@Composable
fun timePicker(Time: MutableState<String>): TimePickerDialog {
    val context = LocalContext.current
    // Get calendar instance
    val mCalendar = Calendar.getInstance()

    // Get current hour and minute
    val mHour = mCalendar.get(Calendar.HOUR_OF_DAY)
    val mMinute = mCalendar.get(Calendar.MINUTE)

    // set calendar time to the current time
    mCalendar.time = Calendar.getInstance().time

    // return a time picker dialog

    return TimePickerDialog(context, {_, hour: Int, minute: Int ->
        Time.value = "$hour: $minute"},
        mHour, mMinute, false)

    }
