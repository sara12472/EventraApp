package com.example.eventra.presentation.HomeScreen

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventra.Models.AddEventUIState
import com.example.eventra.Models.Event
import com.example.eventra.Models.EventMode
import com.example.eventra.Models.EventRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import android.provider.Settings
import java.util.Locale
import javax.inject.Inject




@HiltViewModel
class AddEventViewmodel @Inject constructor(
    private val repository: EventRepository
) : ViewModel() {




    var mode by mutableStateOf(EventMode.Add)
    var eventId by mutableStateOf("")

    var uiState by mutableStateOf(AddEventUIState())

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
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val sharedPref = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        val notificationsEnabled = sharedPref.getBoolean("notificationsEnabled", true)
        if (!notificationsEnabled) {
            Log.d("REMINDER_DEBUG", "Notifications disabled by user, skipping alarm")
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) { // Android 12+
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                Log.d("REMINDER_DEBUG", "Exact alarms not allowed. Request user permission.")
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(intent)
                return
            }
        }


        val alarmTime = eventDateTime - reminderOffset

        Log.d("REMINDER_DEBUG", "EventTime=$eventDateTime")
        Log.d("REMINDER_DEBUG", "AlarmTime=$alarmTime")
        Log.d("REMINDER_DEBUG", "Now=${System.currentTimeMillis()}")

        if (alarmTime <= System.currentTimeMillis()) {
            Log.d("REMINDER_DEBUG", "Alarm skipped (past time)")
            return
        }

        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra("title", title)
            putExtra("detail", detail)
        }
        val requestCode = (eventId.hashCode() and 0xFFFFFFF)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )




        try {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                alarmTime,
                pendingIntent
            )
            Log.d("REMINDER_DEBUG", "Exact alarm scheduled")
        } catch (e: SecurityException) {
            Log.e("REMINDER_DEBUG", "Failed to schedule exact alarm: ${e.message}")
        }
    }
    fun isValid(): Boolean {
        return uiState.eventTitle.isNotBlank() &&
                uiState.eventDate.isNotBlank() &&
                uiState.eventTime.isNotBlank() &&
                uiState.eventLocation.isNotBlank() &&
                uiState.eventDetail.isNotBlank() &&
                uiState.repeatEvent.isNotBlank() &&
                uiState.eventReminder.isNotBlank()
    }
    suspend fun isEventAlreadyExists(date: String, time: String): Boolean {
        val events = repository.getEvents()
        return events.any { it.date == date && it.time == time }
    }


    fun addEvent(context: Context,
                 forceAdd: Boolean = false,
                 onResult: (Boolean, String) -> Unit) {
        Log.d("REMINDER_DEBUG", "addEvent called")

        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        viewModelScope.launch {
            try {


                val exists = isEventAlreadyExists(
                    uiState.eventDate,
                    uiState.eventTime
                )

                if (exists && !forceAdd) {
                    onResult(false, "Event already exists. Do you want to add anyway?")
                    return@launch
                }

                val event = Event(
                    userId = userId,
                    title = uiState.eventTitle,
                    detail = uiState.eventDetail,
                    date = uiState.eventDate,
                    time = uiState.eventTime,
                    location = uiState.eventLocation,
                    repeat = uiState.repeatEvent,
                    reminder = uiState.eventReminder
                )

                repository.addEvent(event)

                scheduleReminderIfNeeded(context)

                onResult(true, "Event added successfully")

            } catch (e: Exception) {
                onResult(false, "Error adding event")
            }
        }
    }

    // UPDATE EVENT
    fun updateEvent(context: Context, onResult: (Boolean) -> Unit) {
        Log.d("REMINDER_DEBUG", "updateEvent called")

        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        Log.d("EVENT_DEBUG","AddEvent clicked userId=$userId")

        viewModelScope.launch {
            try {

                val event = Event(
                    id = eventId,
                    userId = userId,
                    title = uiState.eventTitle,
                    detail = uiState.eventDetail,
                    date = uiState.eventDate,
                    time = uiState.eventTime,
                    location = uiState.eventLocation,
                    repeat = uiState.repeatEvent,
                    reminder = uiState.eventReminder
                )

                Log.d("EVENT_DEBUG","Event object = $event")

                repository.updateEvent(event)

                Log.d("EVENT_DEBUG","Event saved successfully")

                scheduleReminderIfNeeded(context)

                onResult(true)

            } catch (e: Exception) {

                Log.e("EVENT_DEBUG","Error saving event",e)

                onResult(false)
            }
        }
    }
    private fun scheduleReminderIfNeeded(context: Context) {
        Log.d("REMINDER_DEBUG", "scheduleReminderIfNeeded called")


        val dateTimeString = "${uiState.eventDate} ${uiState.eventTime}"

        val formatter =
            SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

        val eventDateTime = try {
            formatter.parse(dateTimeString)?.time
        } catch (e: Exception) {
            null
        }
        Log.d("REMINDER_DEBUG", "uiState.eventDate=${uiState.eventDate}, uiState.eventTime=${uiState.eventTime}")


        if (eventDateTime != null ) {

            scheduleReminder(
                context = context,
                title = uiState.eventTitle,
                detail = uiState.eventDetail,
                eventDateTime = eventDateTime,
                reminderOffset = getReminderOffset(uiState.eventReminder)
            )
        }
    }

    fun fetchEventById(id: String) {

        viewModelScope.launch {

            val event = repository.getEvents().find { it.id == id }

            event?.let {

                eventId = it.id
                mode = EventMode.Update

                uiState = AddEventUIState(
                    eventTitle = it.title,
                    eventDetail = it.detail,
                    eventDate = it.date,
                    eventTime = it.time,
                    eventLocation = it.location,
                    repeatEvent = it.repeat,
                    eventReminder = it.reminder
                )
            }
        }
    }
    fun deleteEvent(event: Event, onResult: (Boolean) -> Unit) {

        viewModelScope.launch {
            try {
                repository.deleteEvent(event)
                onResult(true)
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    fun loadEvent(event: AddEventUIState, id: String) {
        uiState = event
        mode = EventMode.Update
        eventId = id
    }
}