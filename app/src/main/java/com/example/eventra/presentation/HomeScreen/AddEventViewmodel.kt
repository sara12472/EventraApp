package com.example.eventra.presentation.HomeScreen

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.contracts.contract


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
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
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
    fun getReminderOffset(reminder: String): Long {
        return when (reminder) {
            "At time of event" -> 0L
            "5 minutes before" -> 5 * 60 * 1000L
            "10 minutes before" -> 10 * 60 * 1000L
            "30 minutes before" -> 30 * 60 * 1000L
            "1 hour before" -> 60 * 60 * 1000L
            "1 day before" -> 24 * 60 * 60 * 1000L
            else -> 0L
        }
    }

    fun scheduleReminder(
        context: Context,
        title: String,
        detail: String,
        eventDateTime: Long,
        reminderOffset: Long
    ) {
        val alarmTime = eventDateTime - reminderOffset

        val intent = android.content.Intent(context, ReminderReceiver::class.java).apply {
            putExtra("title", title)
            putExtra("detail", detail)
        }

        val pendingIntent = android.app.PendingIntent.getBroadcast(
            context,
            title.hashCode(),
            intent,
            android.app.PendingIntent.FLAG_UPDATE_CURRENT or android.app.PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager =
            context.getSystemService(android.content.Context.ALARM_SERVICE) as android.app.AlarmManager

        alarmManager.setExact(android.app.AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent)
    }
    // Functions to handle Add / Update logic
    fun addEvent(context: Context,onResult: (Boolean) -> Unit,) {
        val currentUser = auth.currentUser
        if (currentUser == null) { onResult(false); return }

        val event = hashMapOf(
            "title" to uiState.eventTitle,
            "detail" to uiState.eventDetail,
            "date" to uiState.eventDate,
            "time" to uiState.eventTime,
            "location" to uiState.eventLocation,
            "repeat" to uiState.repeatEvent,
            "reminder" to uiState.eventReminder,
            "userId" to currentUser.uid,
            "createdAt" to System.currentTimeMillis()
        )

        // Save in top-level 'events' collection
        db.collection("events")
            .add(event)
            .addOnSuccessListener {
                val dateTimeString = "${uiState.eventDate} ${uiState.eventTime}"
                val formatter = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault())
                val eventDateTime = try {
                    formatter.parse(dateTimeString)?.time
                } catch (e: Exception) {
                    null
                }

                if (eventDateTime != null && uiState.eventReminder != "None") {
                    scheduleReminder(
                        context = context,
                        title = uiState.eventTitle,
                        detail = uiState.eventDetail,
                        eventDateTime = eventDateTime,
                        reminderOffset = getReminderOffset(uiState.eventReminder)
                    )
                }
                onResult(true) }
            .addOnFailureListener { onResult(false) }
    }


    fun updateEvent(context: Context,eventId: String, onResult: (Boolean) -> Unit) {
        val currentUser = auth.currentUser
        if (currentUser == null) { onResult(false); return }

        val event = hashMapOf(
            "title" to uiState.eventTitle,
            "detail" to uiState.eventDetail,
            "date" to uiState.eventDate,
            "time" to uiState.eventTime,
            "location" to uiState.eventLocation,
            "repeat" to uiState.repeatEvent,
            "reminder" to uiState.eventReminder,
            "userId" to currentUser.uid
        )

        db.collection("events")
            .document(eventId)
            .set(event)
            .addOnSuccessListener {
                // Reminder logic
                val dateTimeString = "${uiState.eventDate} ${uiState.eventTime}"
                val formatter = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault())
                val eventDateTime = try {
                    formatter.parse(dateTimeString)?.time
                } catch (e: Exception) {
                    null
                }

                if (eventDateTime != null && uiState.eventReminder != "None") {
                    scheduleReminder(
                        context = context,
                        title = uiState.eventTitle,
                        detail = uiState.eventDetail,
                        eventDateTime = eventDateTime,
                        reminderOffset = getReminderOffset(uiState.eventReminder)
                    )
                }

                onResult(true)
            }
            .addOnFailureListener { onResult(false) }
    }

    // Optional: Load existing event for update
    fun loadEvent(event: AddEventUIState) {
        uiState = event
        mode = EventMode.Update
    }
}