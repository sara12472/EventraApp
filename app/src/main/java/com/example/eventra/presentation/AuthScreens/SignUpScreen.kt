package com.example.eventra.presentation.AuthScreens

import AuthLayout
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.eventra.presentation.component.AppButton
import com.example.eventra.presentation.component.AppTextField
import com.example.eventra.ui.theme.mainColor

@Composable
fun SignUpScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }

    AuthLayout(
        showBackButton = true,
        onBackClick = {navController.popBackStack()},
        content = {

            Column(modifier = Modifier.fillMaxSize().padding(top = 25.dp, start = 15.dp, end=10.dp),
                //verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AppTextField(
                    value = name,
                    onValueChange = {name=it},
                    placeholder = "Name",
                    isPassword = false
                )

                Spacer(modifier = Modifier.height(10.dp))
                AppTextField(
                    value = email,
                    onValueChange = {email=it},
                    placeholder = "Email",
                    isPassword = false
                )
                Spacer(modifier = Modifier.height(10.dp))
                AppTextField(
                    value = password,
                    onValueChange = {password=it},
                    placeholder = "Password",
                    isPassword = true
                )
                Spacer(modifier = Modifier.height(10.dp))
                AppTextField(
                    value = confirmPassword,
                    onValueChange = {confirmPassword=it},
                    placeholder = "Confirm Password",
                    isPassword = true
                )
                Spacer(modifier = Modifier.height(40.dp))

                AppButton(
                    text = "SIGN UP",
                    onClick = {},
                    modifier = Modifier.width(276.dp)
                        .height(64.dp)
                )

                Spacer(modifier = Modifier.height(40.dp))
                AuthBottomSection(
                    bottomText = "Don't have an account?",
                    clickableText = "Sign in",
                    onClick = {}
                )




            }

        }

    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignUpPreview(){
    val navController= rememberNavController()
    SignUpScreen(navController)
}