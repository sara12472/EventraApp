package com.example.eventra.presentation.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.FontScalingLinear
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.eventra.presentation.navigation.Screen
import com.example.eventra.ui.theme.AppGradient
import com.example.eventra.ui.theme.greyColor
import com.example.eventra.ui.theme.mainColor
import com.example.eventra.ui.theme.myFont
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun Home(navController: NavController) {

    Scaffold(
        floatingActionButton = {
            // Gradient FAB
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .size(56.dp) // standard FAB size
                    .background(AppGradient, shape = CircleShape), // gradient background
                contentAlignment = Alignment.Center
            ) {
                FloatingActionButton(
                    onClick = { navController.navigate(Screen.AddEvent.route)},
                    containerColor = Color.Transparent, // make FAB transparent so gradient shows
                    contentColor = Color.White,
                    elevation = FloatingActionButtonDefaults.elevation(0.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Add Event",
                        tint = Color.White
                    )
                }
            }
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {

                Box {
                    HomeTopBar()

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 160.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        DatePickerButton(
                            onDateSelected = {},
                            onDismiss = {}
                        )
                    }
                }

                Spacer(modifier = Modifier.height(60.dp))

                // 🔴 Cards Section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {

                    EventsCard(
                        title = "Today's Events",
                        items = todayEvents.map { it.title },
                        backgroundBrush = AppGradient,
                        onClick = {
                            navController.navigate(Screen.TodaysEventDetailScreen.route)
                        },
                        modifier = Modifier.fillMaxWidth() // ensures full width inside Column
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    EventsCard(
                        title = "Upcoming Events",
                        items = upcomingEvents.map { it.title },
                        backgroundColor = greyColor,
                        onClick = {
                            navController.navigate(Screen.UpcomingEventDetailScreen.route)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(100.dp)) // space for FAB
                }
            }
        }
    }
          }








@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerButton(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {

    val datePickerState = rememberDatePickerState()
    var showDialog by remember { mutableStateOf(false) }
    var selectedDateText by remember { mutableStateOf("SELECT DATE") }

    Button(
        colors =ButtonDefaults.buttonColors(containerColor = greyColor), // transparent
        modifier = Modifier.width(303.dp).height(68.dp),
        onClick = {
           showDialog=true
        }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "SELECT DATE",
                fontSize = 24.sp,
                fontFamily= myFont,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                )

            Spacer(modifier = Modifier.width(35.dp))

            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Select Date",
                tint = mainColor,
                modifier = Modifier.height(30.dp).width(30.dp)
            )
        }
    }
  if(showDialog){
      DatePickerDialog(
          onDismissRequest = {showDialog=false},
          confirmButton = {
              TextButton(onClick = {
                  datePickerState.selectedDateMillis?.let { millis ->
                      val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                      selectedDateText = formatter.format(Date(millis))
                  }
                  showDialog = false
              }) {
                  Text("OK")
              }
          },
          dismissButton = {TextButton(onClick = { showDialog = false }) {
              Text("Cancel")
          }},
          content = {},

      )

  }

}

@Preview
@Composable
fun Preview(){
    DatePickerButton(
        onDateSelected = {},
        onDismiss = {}
    )
}

@Preview
@Composable
fun showHome(){
    var navController = rememberNavController()
    Home(navController)
}

/*DatePickerDialog(
onDismissRequest ={},
confirmButton = {
    TextButton(onClick = {
        datePickerState.selectedDateMillis?.let { millis ->
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = millis
            selectedDateText =
                "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(
                    Calendar.YEAR
                )}"
        }
        showDialog = false
    }) {
        Text("OK")
    }
},
dismissButton = {
    TextButton(onClick = {showDialog=false}) {
        Text("Cancel")
    }
}
) {
    androidx.compose.material3.DatePicker(
        state = datePickerState
    )
}*/