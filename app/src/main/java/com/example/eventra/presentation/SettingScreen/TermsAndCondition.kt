package com.example.eventra.presentation.SettingScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import com.example.eventra.R
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.eventra.presentation.component.AppTopBar
import com.example.eventra.ui.theme.mainColor

@Composable
fun TermsAndCondition(navController: NavController) {
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 50.dp, start = 20.dp, end = 20.dp)
    ) {
        AppTopBar(
            text = "Terms & Conditions",
            onBackClick = { navController.popBackStack() },
            color = colors.onBackground
        )

        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.eventralogocolor), // replace with your app logo drawable
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(200.dp) // size of logo
            )
        }


        Spacer(modifier = Modifier.height(20.dp))

        // Centered bold title
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Terms & Conditions",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = mainColor,
                modifier = Modifier
                    .fillMaxWidth(), // full width so center works
                textAlign = TextAlign.Center, // center align
                maxLines = 2,
                overflow = TextOverflow.Ellipsis


            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Bullet points for terms
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text("• Users must provide accurate event information.", color = colors.onBackground)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "• Eventra is not responsible for any event cancellations.",
                color = colors.onBackground
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text("• All user data will be kept private and secure.", color = colors.onBackground)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "• Misuse of the app may result in account termination.",
                color = colors.onBackground
            )
        }
    }
}



    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun ShowTermsAndCondition(){
        val navController= rememberNavController()
        TermsAndCondition(navController)


    }
