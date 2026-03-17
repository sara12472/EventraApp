package com.example.eventra.presentation.AuthScreens

import AuthLayout
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.eventra.presentation.component.AppButton
import com.example.eventra.presentation.component.AppTextField
import com.example.eventra.presentation.navigation.Screen
import com.example.eventra.ui.theme.mainColor
import com.example.eventra.R



@Composable
fun SignInScreen(navController: NavController,
                 ) {
    val context = LocalContext.current
    val viewModel: SignInViewModel = hiltViewModel()
    val email = viewModel.email
    val password = viewModel.password
    val rememberMe = viewModel.rememberMe
    val emailError = viewModel.emailError
    val passwordError = viewModel.passwordError


    AuthLayout(
        showBackButton = true,
        onBackClick = {navController.popBackStack()},
        content = {

            Column(modifier = Modifier.fillMaxSize().padding(top = 50.dp, start = 15.dp, end=10.dp),
                //verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
                ) {
Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
    AppTextField(
        value = email,
        onValueChange = {viewModel.onEmailChange(it)},
        placeholder = "Email",
    )
    if (emailError.isNotEmpty()) {
        ErrorMessageSignIn(emailError)
    }
}
                Spacer(modifier = Modifier.height(15.dp))
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                    AppTextField(
                        value = password,
                        onValueChange = {viewModel.onPasswordChange(it)},
                        placeholder = "Password",
                    )
                    if (passwordError.isNotEmpty()) {
                   ErrorMessageSignIn(passwordError)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.fillMaxWidth().padding(end = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                   // horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                    Row(modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = rememberMe,
                            onCheckedChange = {viewModel.onRememberMeChange(it)}
                        )
                        Text(text = "Remember me", fontSize = 10.sp)

                    }

                    Text(
                        text = "Forgot Password?",
                        color = mainColor,
                        fontSize = 10.sp,
                        modifier = Modifier.clickable {

                                navController.navigate(Screen.ForgetPasswordScreen.route)                            }

                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                AppButton(
                    text = "SIGN IN",
                    onClick = {
                        viewModel.signIn { success, message ->
                            if(success){
                                navController.navigate(Screen.Home.route)
                            }
                        }
                    },
                    modifier = Modifier.width(276.dp)
                        .height(64.dp)
                )

                Spacer(modifier = Modifier.height(40.dp))
                AuthBottomSection(
                    bottomText = "Don't have an account?",
                    clickableText = "Sign up",
                    icons = listOf(
                        R.drawable.google,
                        R.drawable.facebook,
                        R.drawable.apple
                    ),
                    onIconClick = { icon ->
                        when(icon){
                            R.drawable.google -> {



                            }
                            R.drawable.facebook -> {
                                // Facebook login
                            }
                        }
                    },
                    onClick = {
                        navController.navigate(Screen.SignUpScreen.route)
                    }
                )




            }

        }

    )
}


@Preview
@Composable
fun PreviewScreen(){
    val navController= rememberNavController()
    SignInScreen(navController)
}
@Composable
fun ErrorMessageSignIn(
    text: String=""
){
    Text(text, fontSize = 10.sp, color = Color.Red, modifier = Modifier.padding(top = 2.dp, start = 15.dp))


}
