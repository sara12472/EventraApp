package com.example.eventra.Models

interface EventRepository {
    suspend fun addEvent(event: Event)

    suspend fun getEvents(): List<Event>

    suspend fun updateEvent(event: Event)
    suspend fun  deleteEvent(event: Event)
    suspend fun getEventById(eventId: String): Event?

    suspend fun getEventsByDate(date: String): List<Event>

}