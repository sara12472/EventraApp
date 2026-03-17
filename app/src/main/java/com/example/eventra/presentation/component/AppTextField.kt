package com.example.eventra.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventra.ui.theme.ThemeSettings


@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    maxLines:Int=5



)
{
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        minLines = 1,
        maxLines = maxLines,
        label = {
            Text(text = placeholder, color = if (ThemeSettings.isDarkTheme) Color.White else Color.Gray)
        },

        visualTransformation =
            if (isPassword && !passwordVisible)
                PasswordVisualTransformation()
            else
                VisualTransformation.None,

        trailingIcon = {
            when {
                isPassword -> {
                    IconButton(
                        onClick = { passwordVisible = !passwordVisible },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector =
                                if (passwordVisible)
                                    Icons.Default.Visibility
                                else
                                    Icons.Default.VisibilityOff,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                    }
                }

                trailingIcon != null -> {
                    trailingIcon()   // 🔥 THIS WAS MISSING
                }
            }
        },


        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            disabledTextColor = Color.Gray,
           focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            //disabledContainerColor = Color.Transparent,
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Gray,
            unfocusedIndicatorColor = Color.LightGray,
            disabledIndicatorColor = Color.LightGray,
           //focusedPlaceholderColor = Color.Gray,
            //unfocusedPlaceholderColor = Color.Gray,
            //disabledPlaceholderColor = Color.LightGray,
            errorIndicatorColor = Color.Red
        ),

        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        textStyle = androidx.compose.ui.text.TextStyle(
            fontSize = 16.sp,
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview(){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column {
        AppTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = "Email",
        )

        AppTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = "Password",
        )
    }



}