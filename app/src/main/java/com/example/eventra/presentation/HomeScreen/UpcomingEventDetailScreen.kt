package com.example.eventra.presentation.HomeScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.eventra.presentation.component.AppTopBar
import com.example.eventra.presentation.navigation.Screen
import com.example.eventra.ui.theme.AppGradient

@Composable
fun UpcomingEventDetailScreen(navController: NavController,viewModel: HomeViewmodel = viewModel(),
                              addEventViewmodel: AddEventViewmodel=viewModel()
                              ) {
    LaunchedEffect(Unit) {
        viewModel.fetchEvents()
    }
    val upcomingEvents = viewModel.upcomingEvents
    val context= LocalContext.current
    LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 50.dp, start=20.dp, end=20.dp)
        ) {
        item {

            AppTopBar(
                text = "Upcoming Event",
                onBackClick = {
                    navController.popBackStack()
                },
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(20.dp))

        }
        if (upcomingEvents.isEmpty()) {
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "No events today",
                    fontSize = 20.sp,
                    color = Color.Gray
                )
            }
        }
        else{
            items(upcomingEvents) { event ->

                Spacer(modifier = Modifier.height(20.dp))

                EventDetailCard(
                    title = event.title,
                    location = event.location,
                  //  repeat = event.repeat,
                   // reminder = event.reminder,
                    detail = event.detail,
                    icon =  listOf(
                        Icons.Default.Edit,
                        Icons.Default.Share,
                        Icons.Default.Delete
                    ),
                    onIconClick = { icon ->
                        when(icon){
                            Icons.Default.Edit-> {
                                addEventViewmodel.updateEvent(context, event.id) { success ->
                                    if (success) {
                                        Toast.makeText(context, "Event updated successfully", Toast.LENGTH_SHORT).show()
                                        navController.navigate(Screen.Home.route)
                                    }
                                }

                            }
                            Icons.Default.Share -> {
                                // Facebook login
                            }
                            Icons.Default.Delete -> {
                                // Facebook login
                            }
                        }
                    },
                    backgroundBrush = AppGradient,
                    date = event.date,
                    time = event.time

                )

            }
        }


        }
    }

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ShowEventDetailScreen(){


}
