package com.example.eventra.presentation.forgetPasswordScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.tooling.ComposeToolingApi
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.eventra.presentation.AuthScreens.SignInViewModel
import com.example.eventra.presentation.component.AppButton
import com.example.eventra.presentation.component.AppTextField
import com.example.eventra.ui.theme.ThemeSettings
import com.example.eventra.ui.theme.mainColor

@Composable
fun ForgetPasswordScreen(

    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel()

) {
    val bgColor = if (ThemeSettings.isDarkTheme) Color.Black else Color.White
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(80.dp))

        Text(
            text = "Reset Password",
            fontSize = 22.sp,
            color = if (ThemeSettings.isDarkTheme) Color.White else Color.Black

        )

        Spacer(modifier = Modifier.height(30.dp))

        AppTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = "Enter your email",

        )

        Spacer(modifier = Modifier.height(30.dp))

        AppButton(
            text = "SEND RESET EMAIL",
            onClick = {

                viewModel.resetPassword(email) { message ->

                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()

                    if (message.contains("sent")) {
                        navController.popBackStack()
                    }

                }

            },
            modifier = Modifier
                .width(260.dp)
                .height(60.dp)
        )

    }
}