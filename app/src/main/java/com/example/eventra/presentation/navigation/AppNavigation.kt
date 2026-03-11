package com.example.eventra.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eventra.presentation.Onboarding.OnBoardingScreen
import com.example.eventra.presentation.Onboarding.OnboardingAuthScreen
import com.example.eventra.presentation.Onboarding.OnboardingScreenViewmodel
import com.example.eventra.presentation.AuthScreens.SignInScreen
import com.example.eventra.presentation.AuthScreens.SignUpScreen
import com.example.eventra.presentation.HomeScreen.AddEvent
import com.example.eventra.presentation.HomeScreen.Home
import com.example.eventra.presentation.HomeScreen.TodayEventDetailScreen
import com.example.eventra.presentation.HomeScreen.UpcomingEventDetailScreen
import com.example.eventra.presentation.SettingScreen.SettingScreen
import com.example.eventra.presentation.SplashScreen

@Composable
fun AppNavigation() {
    val navController= rememberNavController()

    NavHost(navController=navController, startDestination = Screen.Splash.route){
        composable(Screen.Splash.route){
            SplashScreen(
                onFinish={
                    navController.navigate(Screen.OnBoarding.route){
                        popUpTo(Screen.Splash.route){
                            inclusive=true
                        }
                    }
                }
            )
        }
        composable(Screen.OnBoarding.route) {
            val viewModel: OnboardingScreenViewmodel = viewModel()

            OnBoardingScreen(
                onFinish = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.OnBoarding.route) {
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
            Home(navController)
        }
        composable(Screen.AddEvent.route){
            AddEvent(navController)
        }
        composable(Screen.TodaysEventDetailScreen.route){
            TodayEventDetailScreen(navController)
        }
        composable(Screen.UpcomingEventDetailScreen.route){
            UpcomingEventDetailScreen(navController)
        }
        composable(Screen.SettingScreen.route){
           SettingScreen()
        }


    }

}