package com.example.eventra.presentation.Onboarding

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.eventra.presentation.navigation.Screen
import com.example.eventra.ui.theme.mainColor

@Composable
fun OnBoardingScreen(onFinish: () -> Unit,
                     viewmodel: OnboardingScreenViewmodel,
                     navController: NavController) {

    val currentPage by viewmodel.currentPage.collectAsState()
    val item = onboardingItem[currentPage]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {

        // 🔥 TOP BAR (FIXED)
        OnboardingTopBar(
            showStartIcon = currentPage > 0,
            image = Icons.Default.ArrowBack,
            textButton = if (currentPage == onboardingItem.lastIndex) "" else "SKIP",
            onBackClick = { viewmodel.previousPage() },
            onSkipClick = { viewmodel.nextPage(onboardingItem.size) },
            iconColor = mainColor,
            modifier = Modifier.padding(top = 50.dp) // ✅ FIX
        )

        // 🔥 CENTER CONTENT
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            OnboardingContent(
                image = item.image,
                tittle = item.tittle,
                description = item.description,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // 🔥 BOTTOM BAR (FIXED)
        OnBoardingBottomBar(
            pageCount = onboardingItem.size,
            currentPage = currentPage,
            onNextClick = {
                if (currentPage < onboardingItem.lastIndex) {
                    viewmodel.nextPage(onboardingItem.size)
                } else {

                    val sharedPref =
                        navController.context.getSharedPreferences("myAppPrefs", Context.MODE_PRIVATE)
                    sharedPref.edit().putBoolean("isFirstTimeUser", false).apply()

                    navController.navigate(Screen.OnBoardingAuthScreen.route) {
                        popUpTo(Screen.OnBoardingScreen.route) { inclusive = true }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 50.dp) // ✅ FIX
        )
    }

    }



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnBoardingScreenPreview(){
  val navController =rememberNavController()

    
}