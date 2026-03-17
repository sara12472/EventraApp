package com.example.eventra.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splashScreen")
    object OnBoardingScreen : Screen("onboardingScreen")
    object Home : Screen("Home")
    object OnBoardingAuthScreen : Screen("OnBoardingAuthScreen")
    object SignInScreen : Screen("SignInScreen")
    object SignUpScreen: Screen("SignUpScreen")
    object AddEvent: Screen("add_event") {
        fun withArgs(eventId: String) = "add_event/$eventId"
    }

    object TodaysEventDetailScreen: Screen("TodaysEventDetailScreen")
    object UpcomingEventDetailScreen: Screen("UpcomingEventDetailScreen")
    object SettingScreen: Screen("SettingScreen")
    object ForgetPasswordScreen : Screen("ForgetPasswordScreen")
}