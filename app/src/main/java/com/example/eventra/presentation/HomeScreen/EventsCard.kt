package com.example.eventra.presentation.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.eventra.ui.theme.AppGradient

@Composable
fun EventsCard(
    title: String,
    items: List<String>,
    modifier: Modifier = Modifier,
    backgroundColor: Color? = null,
    backgroundBrush: Brush? = null

    ) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor ?: Color.Transparent
        )
    ) {
        Column(modifier = Modifier.then(
            if (backgroundBrush != null)
                Modifier.background(backgroundBrush)
            else Modifier
        ).padding(12.dp)) {

            Text(
                text = title,
                color =   if (backgroundBrush != null) Color.White else Color.Black,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            items.forEach { item ->
                Text(text = item, color = if (backgroundBrush != null) Color.White else Color.Black,)
            }
        }
    }

}

@Preview
@Composable
fun ShowCard(){
    EventsCard(
        title = "Upcoming event",
        items = listOf("uiux design project", "android app", "app development project"),
        backgroundBrush = AppGradient

    )

}
@Preview
@Composable
fun ShowCard2(){
    EventsCard(
        title = "Upcoming event",
        items = listOf("uiux design project", "android app", "app development project"),
        backgroundColor = Color.White

    )

}

