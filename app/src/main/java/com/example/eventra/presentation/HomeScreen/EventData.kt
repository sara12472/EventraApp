package com.example.eventra.presentation.HomeScreen

data class Event(
    val title: String,
    val location: String,
    val date: String,
    val time: String,
    val repeat: String,
    val reminder: String,
    val detail: String
)
val todayEvents = listOf(
    Event("Morning Workout", "Gym", "05/03/2026", "7:00 AM", "Daily", "15 min before", "Workout session"),
    Event("UI/UX Portfolio Update", "Home", "05/03/2026", "10:00 AM", "Once", "30 min before", "Update portfolio"),
    Event("UI/UX Portfolio Update", "Home", "05/03/2026", "10:00 AM", "Once", "30 min before", "Update portfolio")

)

val upcomingEvents = listOf(
    Event("Flutter Class", "Zoom", "06/03/2026", "5:00 PM", "Weekly", "15 min before", "Flutter basics"),
    Event("Team Meeting", "Office", "07/03/2026", "2:00 PM", "Once", "10 min before", "Project discussion"),
    Event("Team Meeting", "Office", "07/03/2026", "2:00 PM", "Once", "10 min before", "Project discussion")
)
