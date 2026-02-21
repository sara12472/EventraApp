package com.example.eventra.presentation.Onboarding


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import com.example.eventra.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventra.presentation.navigation.Screen
import com.example.eventra.ui.theme.mainColor
import com.example.eventra.ui.theme.myFont


@Composable
fun OnboardingAuthScreen(navController: NavController) {

Box(modifier = Modifier.fillMaxSize()) {
    Image(
        painter = painterResource(id= R.drawable.rectangle),
        contentDescription = "background image",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxHeight()

    )
    OnboardingTopBar(
        showStartIcon = true,
        image = Icons.Default.ArrowBack,
        textButton = "",
        onBackClick = {navController.popBackStack()},
        onSkipClick = {},
        iconColor = Color.White,
        modifier = Modifier.padding(top = 50.dp)
    )

    Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        modifier=Modifier.padding(vertical =20.dp).align(Alignment.Center)
        
        ) {

        Image(
                painter = painterResource(id=R.drawable.pana3),
                modifier = Modifier.height(300.dp).width(300.dp),
                contentDescription = null,

                )


        Spacer(modifier = Modifier.height(40.dp))
            Text(text = "Get started and take\n " +
                    "control of your day",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = myFont,
                color = Color.White,
                textAlign = TextAlign.Center

                )
        Spacer(modifier = Modifier.height(100.dp))
            Text(text = "Access your account and create\n" +
                    "a new one",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = myFont,
                color = Color.White,
                textAlign = TextAlign.Center

            )

        Spacer(modifier = Modifier.height(10.dp))
        Column(modifier = Modifier.padding((20.dp))) {
            AuthButton(text = "SIGN IN", isFilled = true, onClick = {
                navController.navigate(Screen.SignInScreen.route)
            })
            Spacer(modifier = Modifier.height(20.dp))
            AuthButton(text = "SIGN UP", isFilled = false, onClick = {
                navController.navigate(Screen.SignUpScreen.route)
            })

        }

    }

}


        }






@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview(){
val navController=rememberNavController()
    OnboardingAuthScreen(navController = navController)

}

@Composable
fun AuthButton(
    text: String,
    isFilled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (isFilled) {
        Button(
            onClick = onClick,
            modifier = modifier
                .fillMaxWidth()
                .height(64.dp),
            shape = RoundedCornerShape(13.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = mainColor // maroon text
            )
        ) {
            Text(text = text,
                fontFamily = myFont,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold)
        }
    } else {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier
                .fillMaxWidth()
                .height(64.dp),
            shape = RoundedCornerShape(13.dp),
            border = BorderStroke(1.dp, Color.White),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.White
            )
        ) {
            Text(text = text,
                fontFamily = myFont,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold)
        }
    }
}