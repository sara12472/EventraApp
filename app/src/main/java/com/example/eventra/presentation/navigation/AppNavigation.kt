package com.example.eventra.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.eventra.data.EventRepositoryImpl
import com.example.eventra.Models.AddEventUIState
import com.example.eventra.presentation.Onboarding.OnBoardingScreen
import com.example.eventra.presentation.Onboarding.OnboardingAuthScreen
import com.example.eventra.presentation.Onboarding.OnboardingScreenViewmodel
import com.example.eventra.presentation.AuthScreens.SignInScreen
import com.example.eventra.presentation.AuthScreens.SignUpScreen
import com.example.eventra.presentation.HomeScreen.AddEvent
import com.example.eventra.presentation.HomeScreen.AddEventViewmodel
import com.example.eventra.presentation.HomeScreen.Home
import com.example.eventra.presentation.HomeScreen.HomeViewmodel
import com.example.eventra.presentation.HomeScreen.TodayEventDetailScreen
import com.example.eventra.presentation.HomeScreen.UpcomingEventDetailScreen
import com.example.eventra.presentation.SettingScreen.SettingScreen
import com.example.eventra.presentation.SplashScreen
import com.example.eventra.presentation.forgetPasswordScreen.ForgetPasswordScreen
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun AppNavigation() {
    val navController= rememberNavController()
    val firestore = FirebaseFirestore.getInstance()
    val repository = EventRepositoryImpl(firestore)

    val HomeViewModel: HomeViewmodel = hiltViewModel()


    NavHost(navController=navController, startDestination = Screen.Splash.route){
        composable(Screen.Splash.route){
            SplashScreen(
              navController)
        }
        composable(Screen.OnBoardingScreen.route) {
            val viewModel: OnboardingScreenViewmodel = viewModel()

            OnBoardingScreen(
                onFinish = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.OnBoardingScreen.route) {
                            inclusive = true
                        }
                    }
                },
               viewmodel = viewModel, navController
            )
        }
        composable(Screen.OnBoardingAuthScreen.route){
            OnboardingAuthScreen(navController)
        }
        composable(Screen.SignInScreen.route){
            SignInScreen(navController)
        }
        composable(Screen.SignUpScreen.route){
            SignUpScreen(navController)
        }
        composable(Screen.Home.route){
            Home(navController,HomeViewModel)
        }
        composable(
            route = "add_event/{eventId}",
            arguments = listOf(navArgument("eventId") { defaultValue = "new" })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId") ?: "new"
            val addEventViewModel: AddEventViewmodel = hiltViewModel()

            val db = FirebaseFirestore.getInstance()

            // Agar eventId != "new", to edit mode
            LaunchedEffect(eventId) {
                if (eventId != "new") {
                    db.collection("events").
                    document(eventId).get().
                    addOnSuccessListener { doc ->
                        if (doc.exists()) {
                            val event = AddEventUIState(
                                eventTitle = doc.getString("title") ?: "",
                                eventDetail = doc.getString("detail") ?: "",
                                eventDate = doc.getString("date") ?: "",
                                eventTime = doc.getString("time") ?: "",
                                eventLocation = doc.getString("location") ?: "",
                                repeatEvent = doc.getString("repeat") ?: "None",
                                eventReminder = doc.getString("reminder") ?: "None"
                            )
                            addEventViewModel.loadEvent(event, eventId)
                        }
                    }
                }
            }

            AddEvent(navController = navController,
                viewModel = addEventViewModel,
                eventId = eventId)
        }
        composable(Screen.TodaysEventDetailScreen.route){


            TodayEventDetailScreen(navController, HomeViewModel)
        }
        composable(Screen.UpcomingEventDetailScreen.route){

            UpcomingEventDetailScreen(navController,HomeViewModel)
        }
        composable(Screen.SettingScreen.route){
           SettingScreen(navController)
        }
        composable(Screen.ForgetPasswordScreen.route) {
            ForgetPasswordScreen(navController)
        }


    }

}