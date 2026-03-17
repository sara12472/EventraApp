package com.example.eventra.Models

data class AddEventUIState(
    val eventTitle: String = "",
    val eventDetail: String = "",
    val eventDate: String = "",
    val eventTime: String = "",
    val eventLocation: String = "",
    var repeatEvent: String = "None",
    var eventReminder: String = "None"
)