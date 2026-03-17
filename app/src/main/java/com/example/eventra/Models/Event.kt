package com.example.eventra.Models

data class Event(
    val id: String="",
    val userId: String = "",
    val title: String="",
    val detail: String="",
    val date:String="",
    val time:String="",
    val location:String="",
    val repeat:String="",
    val reminder: String=""
)
