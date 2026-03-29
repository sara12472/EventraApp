package com.example.eventra.presentation.HomeScreen

import android.R.attr.action
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.eventra.presentation.component.AppTextField
import com.example.eventra.presentation.component.AppTopBar
import com.example.eventra.ui.theme.AppGradient
import com.example.eventra.ui.theme.greyColor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.material3.TimePickerDialog
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.eventra.presentation.component.AppButton
import com.example.eventra.presentation.navigation.Screen
import com.example.eventra.ui.theme.ThemeSettings
import java.nio.file.WatchEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEvent(navController: NavController,
             viewModel: AddEventViewmodel = hiltViewModel(),
             eventId: String? = null,
             ) {
    LaunchedEffect(eventId) {
        if (eventId != null && eventId != "new") {
            viewModel.fetchEventById(eventId)
        }
    }
    val state = viewModel.uiState
    val bgColor = if (ThemeSettings.isDarkTheme) Color.Black else Color.White
    //val isAddMode = viewModel.mode == EventMode.Add
    val isAddMode = eventId == null || eventId == "new"    //val isAddMode = viewModel.mode == EventMode.Add
    var showDateDialog by remember { mutableStateOf(false) }
    var showTimeDialog by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState(
        is24Hour = true // ✅ YAHAN LAGTA HAI
    )

    val context = LocalContext.current

    var showRepeatMenu by remember { mutableStateOf(false) }
    val repeatOptions = listOf("None", "Daily", "Weekly", "Monthly")


    var showReminderMenu by remember { mutableStateOf(false) }
    val reminderOptions = listOf("None",
        "At time of event",
        "5 minutes before",
        "10 minutes before",
        "30 minutes before",
        "1 hour before",
        "1 day before")
    var showConflictDialog by remember { mutableStateOf(false) }
    /*LaunchedEffect(eventId) {
        if (eventId != null && eventId != "new" ) {
            viewModel.mode = EventMode.Update
            viewModel.eventId = eventId
            viewModel.fetchEventById(eventId) // naya function
        }
    }*/

    Column(modifier = Modifier.fillMaxSize().background(bgColor).
    verticalScroll(rememberScrollState())) {

        // Top bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)

                .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                .background(AppGradient)
                .padding(20.dp)
                .padding(vertical = 30.dp)
        ) {
            AppTopBar(
                if (isAddMode) "Add Event" else "Update Event", Color.White,
                onBackClick = { navController.popBackStack() }
            )
        }
        Column(
            modifier = Modifier
                  // 🔥 IMPORTANT
        ) {

            // Card with inputs
            Box(modifier = Modifier.fillMaxWidth()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp)
                        .offset(y = (-60).dp)
                        .clip(RoundedCornerShape(25.dp))
                        .background(greyColor)
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(top=30.dp, bottom = 30.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                        ) {

                        // Event title
                        AppTextField(
                            value = state.eventTitle ?:"",
                            onValueChange = { viewModel.onTitleChange(it)},
                            placeholder = "Event Title"
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Event date
                        AppTextField(
                            value = state.eventDate,
                            onValueChange = {},
                            placeholder = "Select Date",
                            trailingIcon = {
                                IconButton(onClick = { showDateDialog = true }) {
                                    Icon(
                                        imageVector = Icons.Default.DateRange,
                                        contentDescription = "Select Date"
                                    )
                                }
                            }
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Event time
                        AppTextField(
                            value = state.eventTime,
                            onValueChange = {},
                            placeholder = "Select Time",
                            trailingIcon = {
                                IconButton(onClick = { showTimeDialog = true }) {
                                    Icon(
                                        imageVector = Icons.Default.Timelapse,
                                        contentDescription = "Select Time"
                                    )
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        AppTextField(
                            value = state.eventLocation,
                            onValueChange = {viewModel.onLocationChange(it)},
                            placeholder = "Location",
                        )
                        Spacer(modifier = Modifier.height(10.dp))


                        AppTextField(
                            value = state.repeatEvent, // <-- selected option shown here
                            onValueChange = {},  // read-only
                            placeholder = "Repeat Event",
                            trailingIcon = {
                                IconButton(onClick = { showRepeatMenu = true }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowDropDown,
                                        contentDescription = "Select Repeat Option"
                                    )
                                }
                            },

                            )
                        Spacer(modifier = Modifier.height(10.dp))

                        AppTextField(
                            value = state.eventReminder, // <-- selected option shown here
                            onValueChange = {},  // read-only
                            placeholder = "Event Reminder",
                            trailingIcon = {
                                IconButton(onClick = { showReminderMenu = true }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowDropDown,
                                        contentDescription = "Select Repeat Option"
                                    )
                                }
                            },

                            )
                        Spacer(modifier = Modifier.height(10.dp))
                        AppTextField(
                            value = state.eventDetail,
                            onValueChange = { viewModel.onDetailChange(it) },
                            placeholder = "Event Detail",
                            maxLines = 5
                        )
                        Spacer(modifier = Modifier.height(30.dp))

                        AppButton(
                            text=if (isAddMode) "ADD EVENT" else "UPDATE EVENT",
                            onClick = {
                                if (!viewModel.isValid()) {
                                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                                    return@AppButton
                                }

                                Log.d("REMINDER_DEBUG", "Button clicked")
                                val action: (Boolean) -> Unit = { success ->

                                if (success) {

                                    Toast.makeText(
                                        context,
                                        if (isAddMode) "Event added" else "Event updated",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    navController.popBackStack()

                                } else {

                                    Toast.makeText(
                                        context,
                                        "Operation failed",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                                if (isAddMode) {

                                    viewModel.addEvent(context) { success, message ->

                                        if (!success && message.contains("already exists")) {
                                            showConflictDialog = true // 🔥 show dialog
                                        } else {
                                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                            if (success) navController.popBackStack()
                                        }
                                    }

                                } else {

                                    viewModel.updateEvent(context, action)

                                }
                            },
                            modifier = Modifier.width(276.dp)
                                .height(64.dp)
                        )


                    }
                }


            }


        }

        if (showReminderMenu) {
            androidx.compose.material3.DropdownMenu(
                expanded = showReminderMenu,
                onDismissRequest = { showReminderMenu = false }
            ) {
                reminderOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },  // <-- fix here
                        onClick = {
                            viewModel.onReminderChange(option)
                            showReminderMenu = false
                        }
                    )
                }
            }
        }

        if (showRepeatMenu) {
            androidx.compose.material3.DropdownMenu(
                expanded = showRepeatMenu,
                onDismissRequest = { showRepeatMenu = false }
            ) {
                repeatOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },  // <-- fix here
                        onClick = {
                          viewModel.onRepeatChange(option)
                            showRepeatMenu = false
                        }
                    )
                }
            }
        }




            if (showTimeDialog) {
               TimePickerDialog(
                    onDismissRequest = { showTimeDialog = false },
                    title = { Text("Select Time") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                val hour = timePickerState.hour
                                val minute = timePickerState.minute
                                viewModel.onTimeChange(String.format("%02d:%02d", hour, minute))
                                showTimeDialog = false
                            }
                        ) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showTimeDialog = false }) {
                            Text("Cancel")
                        }
                    }
                ) {
                    TimePicker(
                        state = timePickerState,
                       // ✅ MUST ADD

                        // optional parameters like is24Hour can go here
                        // is24Hour = true
                    )
                }
            }


        // Date Picker Dialog
        if (showDateDialog) {
            DatePickerDialog(
                onDismissRequest = { showDateDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            viewModel.onDateChange(formatter.format(Date(millis)))
                        }
                        showDateDialog = false
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDateDialog = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
        if (showConflictDialog) {
            androidx.compose.material3.AlertDialog(
                onDismissRequest = { showConflictDialog = false },
                title = { Text("Warning") },
                text = { Text("Event already exists at this time. Do you want to add anyway?") },

                confirmButton = {
                    TextButton(onClick = {
                        showConflictDialog = false

                        // 🔥 FORCE ADD
                        viewModel.addEvent(context, forceAdd = true) { success, message ->
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            if (success) navController.popBackStack()
                        }
                    }) {
                        Text("Add Anyway")
                    }
                },

                dismissButton = {
                    TextButton(onClick = {
                        showConflictDialog = false
                    }) {
                        Text("Cancel")
                    }
                }
            )
        }


    }


}



