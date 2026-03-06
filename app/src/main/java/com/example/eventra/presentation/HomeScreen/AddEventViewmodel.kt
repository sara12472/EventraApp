package com.example.eventra.presentation.HomeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class AddEventUIState(
    val eventTitle: String = "",
    val eventDetail: String = "",
    val eventDate: String = "",
    val eventTime: String = "",
    val eventLocation: String = "",
    var repeatEvent: String = "None",
    var eventReminder: String = "OFF"
)

enum class EventMode{
    Add,
    Update
}


class AddEventViewmodel : ViewModel() {
    var mode by mutableStateOf(EventMode.Add)


    var  uiState by mutableStateOf(AddEventUIState())

    fun onTitleChange(value: String) {
        uiState = uiState.copy(eventTitle = value)
    }

    fun onDetailChange(value: String) {
        uiState = uiState.copy(eventDetail = value)
    }

    fun onDateChange(value: String) {
        uiState = uiState.copy(eventDate = value)
    }

    fun onTimeChange(value: String) {
        uiState = uiState.copy(eventTime = value)
    }

    fun onLocationChange(value: String) {
        uiState = uiState.copy(eventLocation = value)
    }

    fun onRepeatChange(value: String) {
        uiState = uiState.copy(repeatEvent = value)
    }

    fun onReminderChange(value: String) {
        uiState = uiState.copy(eventReminder = value)
    }
    // Functions to handle Add / Update logic
    fun addEvent() {
        // TODO: Save event to database
    }

    fun updateEvent() {
        // TODO: Update event in database
    }

    // Optional: Load existing event for update
    fun loadEvent(event: AddEventUIState) {
        uiState = event
        mode = EventMode.Update
    }
}