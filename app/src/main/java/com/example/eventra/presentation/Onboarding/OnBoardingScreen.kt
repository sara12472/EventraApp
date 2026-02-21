package com.example.eventra.presentation.Onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.eventra.presentation.navigation.Screen
import com.example.eventra.ui.theme.mainColor

@Composable
fun OnBoardingScreen(onFinish: () -> Unit, viewmodel: OnboardingScreenViewmodel, navController: NavController) {

    val currentPage by viewmodel.currentPage.collectAsState()
    Column(modifier = Modifier.fillMaxSize()) {


        OnboardingTopBar(
            showStartIcon = currentPage > 0,
            image = Icons.Default.ArrowBack,
            textButton =if (currentPage == onboardingItem.lastIndex) "" else "SKIP",
            onBackClick = { viewmodel.previousPage() },
            onSkipClick = {
                                viewmodel.nextPage(onboardingItem.size)
                          },
            iconColor = mainColor,
            modifier = Modifier.padding(top = 50.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))
        val item = onboardingItem[currentPage]

        OnboardingContent(
            image = item.image,
            tittle = item.tittle,
            description = item.description,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.height(160.dp))

        OnBoardingBottomBar(
            pageCount = onboardingItem.size,
            currentPage = currentPage,

            onNextClick = {
                if (currentPage < onboardingItem.lastIndex) {
                    viewmodel.nextPage(onboardingItem.size)
                } else
                    navController.navigate(Screen.OnBoardingAuthScreen.route)
            }
        )


    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnBoardingScreenPreview(){
  val navController =rememberNavController()

    
}