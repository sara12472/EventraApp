package com.example.eventra.presentation.HomeScreen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.eventra.presentation.component.AppTopBar
import com.example.eventra.ui.theme.AppGradient

@Composable
fun TodayEventDetailScreen(navController: NavController) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 50.dp, start=20.dp, end=20.dp)
    ) {
        item {

            AppTopBar(
                text = "Today's Event",
                onBackClick = {
                  navController.popBackStack()
                },
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(20.dp))

        }

        items(todayEvents) { event ->

            Spacer(modifier = Modifier.height(20.dp))

            EventDetailCard(
                title = event.title,
                location = event.location,
                repeat = event.repeat,
                reminder = event.reminder,
                detail = event.detail,
                onEditClick = {},
                onDeleteClick = {},
                onShareClick = {},
                backgroundBrush = AppGradient,
                date = event.date,
                time = event.time

            )

        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable()
fun ShowScreen(){
    val navController= rememberNavController()
    TodayEventDetailScreen(navController)
}