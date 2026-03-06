package com.example.eventra.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splashScreen")
    object OnBoarding : Screen("onboardingScreen")
    object Home : Screen("Home")
    object OnBoardingAuthScreen : Screen("OnBoardingAuthScreen")
    object SignInScreen : Screen("SignInScreen")
    object SignUpScreen: Screen("SignUpScreen")
    object AddEvent: Screen("AddEvent")
    object UpdateEvent: Screen("UpdateEvent")
    object TodaysEventDetailScreen: Screen("TodaysEventDetailScreen")
    object UpcomingEventDetailScreen: Screen("UpcomingEventDetailScreen")
}