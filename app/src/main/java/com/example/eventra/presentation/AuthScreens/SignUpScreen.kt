package com.example.eventra.presentation.AuthScreens

import AuthLayout
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.eventra.R
import com.example.eventra.presentation.component.AppButton
import com.example.eventra.presentation.component.AppTextField
import com.example.eventra.presentation.navigation.Screen
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.serialization.descriptors.PrimitiveKind

@Composable
fun SignUpScreen(navController: NavController, ) {
    val context = LocalContext.current
    val viewModel: SignUpScreenViewModel = hiltViewModel()
    val signInViewmodel: SignInViewModel=hiltViewModel()

   val name = viewModel.name
    val email = viewModel.email
    val password = viewModel.password
    val confirmPassword = viewModel.confirmPassword
    val rememberMe = viewModel.rememberMe


    val message = viewModel.signupMessage
    val success = viewModel.signupSuccess



    val googleSignInClient = GoogleSignIn.getClient(
        context,
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    )

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->

        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken

            if (idToken != null) {
               signInViewmodel.signInWithGoogle(idToken) { success, message ->
                    if (success) {
                        navController.navigate(Screen.Home.route)
                    } else {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        } catch (e: Exception) {
            Toast.makeText(context, "Google Sign-In Failed", Toast.LENGTH_SHORT).show()
        }
    }


    AuthLayout(
        showBackButton = true,
        onBackClick = {navController.popBackStack()},
        content = {

            Column(modifier = Modifier.fillMaxSize().padding(top = 25.dp, start = 15.dp, end=10.dp),
                //verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                    AppTextField(
                        value = name,
                        onValueChange = {viewModel.onNameChange(it)},
                        placeholder = "Name",
                    )
                    if (viewModel.nameError) {
                        ErrorMessage("* Required")
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                    AppTextField(
                        value = email,
                        onValueChange = {viewModel.onEmailChange(it)},
                        placeholder = "Email",
                    )
                    if (viewModel.emailError.isNotEmpty()) {
                        ErrorMessage(viewModel.emailError)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                    AppTextField(
                        value = password,
                        onValueChange = { viewModel.onPasswordChange(it) },
                        placeholder = "Password",
                        isPassword = true,
                    )
                    if (viewModel.passwordError) {
                        ErrorMessage("* Required")
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                    AppTextField(
                        value = confirmPassword,
                        onValueChange = { viewModel.onConfirmPasswordChange(it) },
                        placeholder = "Confirm Password",
                        isPassword = true,
                    )
                    if (viewModel.confirmPasswordError.isNotEmpty()) {
                        ErrorMessage(viewModel.confirmPasswordError)
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                AppButton(
                    text = "SIGN UP",
                    onClick = {
                        viewModel.signUp { success, message ->
                            if (success) navController.navigate(Screen.SignInScreen.route)
                            else Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.width(276.dp)
                        .height(64.dp)
                )

                Spacer(modifier = Modifier.height(40.dp))
                AuthBottomSection(
                    bottomText = "Already have an account?",
                    clickableText = "Sign in",
                    icons = listOf(
                        R.drawable.google,
                        R.drawable.facebook,
                        R.drawable.apple
                    ),
                    onIconClick = { icon ->
                        when(icon){
                            R.drawable.google -> {

                                launcher.launch(googleSignInClient.signInIntent)


                            }
                            R.drawable.facebook -> {
                                // Facebook login
                            }
                        }
                    },
                    onClick = {
                        navController.navigate(Screen.SignInScreen.route)
                    }
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
@Composable
fun ErrorMessage(
     text: String=""
){
    Text(text, fontSize = 10.sp, color = Color.Red, modifier = Modifier.padding(top = 2.dp, start = 15.dp))


}