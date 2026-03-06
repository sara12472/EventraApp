package com.example.eventra.presentation.AuthScreens

import AuthLayout
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.eventra.presentation.component.AppButton
import com.example.eventra.presentation.component.AppTextField

@Composable
fun SignUpScreen(navController: NavController, viewModel: SignUpScreenViewModel= viewModel()) {

   val name = viewModel.name
    val email = viewModel.email
    val password = viewModel.password
    val confirmPassword = viewModel.confirmPassword
    val rememberMe = viewModel.rememberMe



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
                    onValueChange = {viewModel.onNameChange(it)},
                    placeholder = "Name",
                )

                Spacer(modifier = Modifier.height(10.dp))
                AppTextField(
                    value = email,
                    onValueChange = {viewModel.onEmailChange(it)},
                    placeholder = "Email",
                )
                Spacer(modifier = Modifier.height(10.dp))
                AppTextField(
                    value = password,
                    onValueChange = {viewModel.onPasswordChange(it)},
                    placeholder = "Password",
                    isPassword = true,
                )
                Spacer(modifier = Modifier.height(10.dp))
                AppTextField(
                    value = confirmPassword,
                    onValueChange = {viewModel.onConfirmPasswordChange(it)},
                    placeholder = "Confirm Password",
                    isPassword = true,
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