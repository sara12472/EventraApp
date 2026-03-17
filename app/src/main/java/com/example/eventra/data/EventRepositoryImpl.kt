package com.example.eventra.data

import com.example.eventra.Models.Event
import com.example.eventra.Models.EventRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor (private val firestore: FirebaseFirestore): EventRepository {
    override suspend fun addEvent(event: Event) {
        val docRef = firestore.collection("events").document()

        val eventWithId = event.copy(id = docRef.id)

        docRef.set(eventWithId).await()
    }

    override suspend fun getEvents(): List<Event> {
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        val snapshot = firestore.collection("events")
            .whereEqualTo("userId", userId)
            .get()
            .await()

        return snapshot.documents.map { doc ->
            Event(
                id = doc.id,
                userId = doc.getString("userId") ?: "",
                title = doc.getString("title") ?: "",
                detail = doc.getString("detail") ?: "",
                date = doc.getString("date") ?: "",
                time = doc.getString("time") ?: "",
                location = doc.getString("location") ?: "",
                repeat = doc.getString("repeat") ?: "",
                reminder = doc.getString("reminder") ?: ""
            )
        }
    }

    override suspend fun updateEvent(event: Event) {
        firestore.collection("events")
            .document(event.id)
            .set(event)
            .await()
    }

    override suspend fun deleteEvent(event: Event) {
        firestore.collection("events")
            .document(event.id)
            .delete()
            .await()
    }

    override suspend fun getEventById(eventId: String): Event? {
        val doc = firestore.collection("events")
            .document(eventId)
            .get()
            .await()

        return if (doc.exists()) {
            Event(
                id = doc.id,
                userId = doc.getString("userId") ?: "",
                title = doc.getString("title") ?: "",
                detail = doc.getString("detail") ?: "",
                date = doc.getString("date") ?: "",
                time = doc.getString("time") ?: "",
                location = doc.getString("location") ?: "",
                repeat = doc.getString("repeat") ?: "",
                reminder = doc.getString("reminder") ?: ""
            )
        } else {
            null
        }
    }


   override suspend fun getEventsByDate(date: String): List<Event> {
       val userId = FirebaseAuth.getInstance().currentUser?.uid

       val snapshot = firestore.collection("events")
           .whereEqualTo("userId", userId)
           .whereEqualTo("date", date)
           .get()
           .await()

        return snapshot.documents.map { doc ->
            Event(
                id = doc.id,
                userId = doc.getString("userId") ?: "",
                title = doc.getString("title") ?: "",
                detail = doc.getString("detail") ?: "",
                date = doc.getString("date") ?: "",
                time = doc.getString("time") ?: "",
                location = doc.getString("location") ?: "",
                repeat = doc.getString("repeat") ?: "",
                reminder = doc.getString("reminder") ?: ""
            )
        }
    }
}