package com.example.eventra.presentation.HomeScreen

import android.R.attr.title
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventra.Models.Event
import com.example.eventra.Models.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class HomeViewmodel @Inject constructor (private val repository: EventRepository) : ViewModel() {
    //private val db = FirebaseFirestore.getInstance()

    var selectedDate by mutableStateOf("SELECT DATE")
        private set

    var showDateDialog by mutableStateOf(false)

    var selectedEvents by mutableStateOf<List<Event>>(emptyList())

    var todayEvents by mutableStateOf<List<Event>>(emptyList())
        private set

    var upcomingEvents by mutableStateOf<List<Event>>(emptyList())
        private set


    fun openDateDialog() {
        showDateDialog = true
    }

    fun closeDateDialog() {
        showDateDialog = false
    }

    fun clearDate() {
        selectedDate = ""
        selectedEvents = emptyList()
    }

    fun setDate(date: String) {
        selectedDate = date
        fetchEventForDate(date)
    }
    fun fetchEvents() {
        viewModelScope.launch {
            try {
                val allEvents = repository.getEvents()
                val today = Date()
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val todayStr = sdf.format(today)
                val todayDayName = SimpleDateFormat("EEEE", Locale.getDefault()).format(today)
                val todayDayNumber = SimpleDateFormat("dd", Locale.getDefault()).format(today)

                todayEvents = allEvents.filter { event ->
                    val eventDate = sdf.parse(event.date) ?: today
                    val eventDayName = SimpleDateFormat("EEEE", Locale.getDefault()).format(eventDate)
                    val eventDayNumber = SimpleDateFormat("dd", Locale.getDefault()).format(eventDate)

                    when (event.repeat) {
                        "Daily" -> true
                        "Weekly" -> eventDayName == todayDayName
                        "Monthly" -> eventDayNumber == todayDayNumber
                        else -> event.date == todayStr
                    }
                }

                upcomingEvents = allEvents.filter { event ->
                    val eventDate = sdf.parse(event.date) ?: today
                    when (event.repeat) {
                        "Daily", "Weekly", "Monthly" -> eventDate.after(today)
                        else -> eventDate.after(today)
                    }
                }

            } catch (e: Exception) {
                todayEvents = emptyList()
                upcomingEvents = emptyList()
            }
        }
    }

    // Fetch events for a specific date
    private fun fetchEventForDate(date: String) {
        viewModelScope.launch {
            try {
                val allEvents = repository.getEvents()
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val selected = sdf.parse(date)
                val selectedDayName = SimpleDateFormat("EEEE", Locale.getDefault()).format(selected!!)
                val selectedDayNumber = SimpleDateFormat("dd", Locale.getDefault()).format(selected)

                selectedEvents = allEvents.filter { event ->
                    val eventDate = sdf.parse(event.date)
                    val eventDayName = SimpleDateFormat("EEEE", Locale.getDefault()).format(eventDate!!)
                    val eventDayNumber = SimpleDateFormat("dd", Locale.getDefault()).format(eventDate)

                    event.date == date ||
                            event.repeat == "Daily" ||
                            (event.repeat == "Weekly" && eventDayName == selectedDayName) ||
                            (event.repeat == "Monthly" && eventDayNumber == selectedDayNumber)
                }
            } catch (e: Exception) {
                selectedEvents = emptyList()
            }
        }
    }

    // Delete event
    fun deleteEvent(event: Event, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                repository.deleteEvent(event)
                fetchEvents() // refresh
                onResult(true)
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    // Schedule reminder

    fun resetDate(){
        selectedDate = "SELECT DATE"
        selectedEvents = emptyList()
    }


}
    
