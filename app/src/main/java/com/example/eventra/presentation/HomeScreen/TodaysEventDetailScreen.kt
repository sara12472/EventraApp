package com.example.eventra.presentation.HomeScreen

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.eventra.R
import com.example.eventra.presentation.component.AppTopBar
import com.example.eventra.presentation.navigation.Screen
import com.example.eventra.ui.theme.AppGradient
import com.example.eventra.ui.theme.ThemeSettings
import com.example.eventra.ui.theme.darkGradient

@Composable
fun TodayEventDetailScreen(navController: NavController,viewModel: HomeViewmodel,
                           addEventViewmodel:  AddEventViewmodel= hiltViewModel()
                           ) {
    val todayEvents = viewModel.todayEvents ?: emptyList()
    val context= LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.fetchEvents()
    }
    val bgColor = if (ThemeSettings.isDarkTheme) Color.Black else Color.White
    val textColor = if (ThemeSettings.isDarkTheme) Color.White else Color.Black
    Box(modifier = Modifier.fillMaxSize().background(bgColor)){
    LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 50.dp, start=20.dp, end=20.dp).background(bgColor)
    ) {
        item {

            AppTopBar(
                text = "Today's Event",
                onBackClick = {
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    }
                },
                color = textColor
            )

            Spacer(modifier = Modifier.height(20.dp))

        }
        if (todayEvents.isEmpty()) {
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "No events today",
                    fontSize = 20.sp,
                    color = Color.Gray
                )
            }
        } else {
            items(todayEvents) { event ->

                Spacer(modifier = Modifier.height(20.dp))

                EventDetailCard(
                    title = event.title,
                    location = event.location,
                    //  repeat = event.repeat,
                    //reminder = event.reminder,
                    detail = event.detail,
                    backgroundBrush = if (ThemeSettings.isDarkTheme)
                        darkGradient // define dark gradient
                    else
                        AppGradient,
                    date = event.date,
                    time = event.time,
                    icon = listOf(
                        Icons.Default.Edit,
                        Icons.Default.Share,
                        Icons.Default.Delete
                    ),
                    onIconClick = { icon ->
                        when (icon) {
                            Icons.Default.Edit -> {
                                /* addEventViewmodel.loadEvent(
                                    AddEventUIState(
                                        eventTitle = event.title,
                                        eventDetail = event.detail,
                                        eventDate = event.date,
                                        eventTime = event.time,
                                        eventLocation = event.location,
                                        repeatEvent = event.repeat,
                                        eventReminder = event.reminder
                                    ),
                                    event.id
                                )*/

                                navController.navigate("add_event/${event.id}")

                            }

                            Icons.Default.Share -> {
                                val shareText = """
                                Event: ${event.title}
                                Date: ${event.date}
                                Time: ${event.time}
                                Location: ${event.location}

                               ${event.detail}
                               """.trimIndent()
                                val intent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_TEXT, shareText)
                                }

                                context.startActivity(
                                    Intent.createChooser(intent, "Share Event")
                                )
                            }

                            Icons.Default.Delete -> {
                                addEventViewmodel.deleteEvent(event) { success ->

                                    if (success) {
                                        Toast.makeText(context, "Event Deleted", Toast.LENGTH_SHORT)
                                            .show()

                                        viewModel.fetchEvents() // list refresh
                                    }

                                }

                            }
                        }
                    },
                )

            }
        }
    }

    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable()
fun ShowScreen(){

}