package com.example.eventra.presentation.HomeScreen

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeViewmodel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

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
        selectedEvents=emptyList()
    }

    fun setDate(date: String) {
        selectedDate = date
        fetchEventForDate(date)
    }
    private fun fetchEventForDate(date: String) {
        val currentUser = FirebaseAuth.getInstance().currentUser ?: return

        db.collection("events")
            .whereEqualTo("userId", currentUser.uid)
            .get()
            .addOnSuccessListener { documents ->
                val events = documents.map { doc ->
                    Event(
                        title = doc.getString("title") ?: "",
                        detail = doc.getString("detail") ?: "",
                        date = doc.getString("date") ?: "",
                        time = doc.getString("time") ?: "",
                        location = doc.getString("location") ?: "",
                        repeat = doc.getString("repeat") ?: "",
                        reminder = doc.getString("reminder") ?: ""
                    )
                }

                // filter selected events including repeats
                selectedEvents = events.filter { event ->
                    val eventDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(event.date)
                    val selected = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(date)

                    val eventDayName = SimpleDateFormat("EEEE", Locale.getDefault()).format(eventDate!!)
                    val selectedDayName = SimpleDateFormat("EEEE", Locale.getDefault()).format(selected!!)

                    val eventDayNumber = SimpleDateFormat("dd", Locale.getDefault()).format(eventDate)
                    val selectedDayNumber = SimpleDateFormat("dd", Locale.getDefault()).format(selected)

                    // include repeats
                    event.date == date ||
                            event.repeat == "Daily" ||
                            (event.repeat == "Weekly" && eventDayName == selectedDayName) ||
                            (event.repeat == "Monthly" && eventDayNumber == selectedDayNumber)
                }
            }
            .addOnFailureListener {
                selectedEvents = emptyList()
            }
    }
    // Fetch today & upcoming events
    fun fetchEvents() {
        val currentUser = FirebaseAuth.getInstance().currentUser ?: return

        val today = Date()

        val todayDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(today)
        val todayDayName = SimpleDateFormat("EEEE", Locale.getDefault()).format(today)
        val todayDayNumber = SimpleDateFormat("dd", Locale.getDefault()).format(today)

        db.collection("events")
            .whereEqualTo("userId", currentUser.uid)
            .get()
            .addOnSuccessListener { documents ->

                val events = documents.map { doc ->
                    Event(
                        id = doc.id,
                        title = doc.getString("title") ?: "",
                        detail = doc.getString("detail") ?: "",
                        date = doc.getString("date") ?: "",
                        time = doc.getString("time") ?: "",
                        location = doc.getString("location") ?: "",
                        repeat = doc.getString("repeat") ?: "",
                        reminder = doc.getString("reminder") ?: ""
                    )
                }

                todayEvents = events.filter { event ->

                    val eventDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        .parse(event.date)

                    val eventDayName =
                        SimpleDateFormat("EEEE", Locale.getDefault()).format(eventDate!!)

                    val eventDayNumber =
                        SimpleDateFormat("dd", Locale.getDefault()).format(eventDate)

                    event.date == todayDate ||
                            event.repeat == "Daily" ||
                            (event.repeat == "Weekly" && eventDayName == todayDayName) ||
                            (event.repeat == "Monthly" && eventDayNumber == todayDayNumber)
                }

                upcomingEvents = events.filter { event ->

                    val eventDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        .parse(event.date)

                    val eventDayName =
                        SimpleDateFormat("EEEE", Locale.getDefault()).format(eventDate!!)

                    val eventDayNumber =
                        SimpleDateFormat("dd", Locale.getDefault()).format(eventDate)

                    !(event.date == todayDate ||
                            event.repeat == "Daily" ||
                            (event.repeat == "Weekly" && eventDayName == todayDayName) ||
                            (event.repeat == "Monthly" && eventDayNumber == todayDayNumber))
                }
            }
    }


    }
    
