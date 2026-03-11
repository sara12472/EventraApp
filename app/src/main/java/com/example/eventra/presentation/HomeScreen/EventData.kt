package com.example.eventra.presentation.HomeScreen

data class Event(
    val id: String = "",
    val title: String,
    val location: String,
    val date: String,
    val time: String,
    val repeat: String,
    val reminder: String,
    val detail: String
)

