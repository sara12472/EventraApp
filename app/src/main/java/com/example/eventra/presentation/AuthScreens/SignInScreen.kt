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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.eventra.presentation.component.AppButton
import com.example.eventra.presentation.component.AppTextField
import com.example.eventra.presentation.navigation.Screen
import com.example.eventra.ui.theme.mainColor
import com.example.eventra.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlin.jvm.java


@Composable
fun SignInScreen(navController: NavController,
                 viewModel: SignInViewModel = viewModel()
                 ) {
    val email = viewModel.email
    val password = viewModel.password
    val rememberMe = viewModel.rememberMe

    val context = LocalContext.current

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    val googleSignInClient = GoogleSignIn.getClient(context, gso)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            account?.idToken?.let { idToken ->
                viewModel.firebaseAuthWithGoogle(idToken) { success, message ->
                    if(success) {
                        navController.navigate(Screen.Home.route)
                    } else {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } catch (e: ApiException) {
            Toast.makeText(context, "Google Sign-In Failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    AuthLayout(
        showBackButton = true,
        onBackClick = {navController.popBackStack()},
        content = {

            Column(modifier = Modifier.fillMaxSize().padding(top = 50.dp, start = 15.dp, end=10.dp),
                //verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
                ) {

                AppTextField(
                    value = email,
                    onValueChange = {viewModel.onEmailChange(it)},
                    placeholder = "Email",
                )

                Spacer(modifier = Modifier.height(15.dp))
                AppTextField(
                    value = password,
                    onValueChange = {viewModel.onPasswordChange(it)},
                    placeholder = "Password",
                )
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
                        modifier = Modifier.clickable { }
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
                                val signInIntent = googleSignInClient.signInIntent
                                launcher.launch(signInIntent)


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
