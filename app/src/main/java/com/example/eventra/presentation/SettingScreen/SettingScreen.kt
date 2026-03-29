package com.example.eventra.presentation.SettingScreen

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BrandingWatermark
import androidx.compose.material.icons.filled.Cookie
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.eventra.presentation.AuthScreens.SignInViewModel
import com.example.eventra.presentation.HomeScreen.HomeViewmodel
import com.example.eventra.presentation.component.AppTopBar
import com.example.eventra.presentation.navigation.Screen
import com.example.eventra.ui.theme.ThemeSettings
import com.example.eventra.ui.theme.myFont
import java.nio.file.WatchEvent


@Composable
fun SettingScreen(
    navController: NavController,
    signInViewmodel: SignInViewModel = hiltViewModel()
) {
    val colors = MaterialTheme.colorScheme
    val context = LocalContext.current
    var notificationsEnabled by remember { mutableStateOf(true) } // default true
    val sharedPref = LocalContext.current.getSharedPreferences("app_settings",
        Context.MODE_PRIVATE)
    LaunchedEffect(Unit) {
        notificationsEnabled = sharedPref.getBoolean("notificationsEnabled", true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background) // Full screen background
            .padding(top = 50.dp, start = 20.dp, end = 20.dp)
    ) {
        AppTopBar(
            text = "Setting",
            onBackClick = { navController.popBackStack() },
            color = colors.onBackground
        )

        Spacer(modifier = Modifier.height(50.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    notificationsEnabled = !notificationsEnabled
                    val editor = sharedPref.edit()
                    editor.putBoolean("notificationsEnabled", notificationsEnabled)
                    editor.apply()
                    // Save preference

                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (notificationsEnabled) Icons.Default.NotificationsActive else Icons.Default.NotificationsOff,
                contentDescription = null,
                tint = colors.onBackground
            )
            Spacer(modifier = Modifier.width(30.dp))
            Text(
                text = if (notificationsEnabled) "Notifications On" else "Notifications Off",
                fontFamily = myFont,
                fontSize = 18.sp,
                color = colors.onBackground
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        IconText(
            icon = Icons.Default.DarkMode,
            text = if (ThemeSettings.isDarkTheme) "Dark Mode On" else "Light Mode",
            iconTint = colors.onBackground,
            textColor = colors.onBackground,
            onClick = { ThemeSettings.isDarkTheme = !ThemeSettings.isDarkTheme }
        )

        Spacer(modifier = Modifier.height(40.dp))

        IconText(
            icon = Icons.Default.RateReview,
            text = "Rate App",
            iconTint = colors.onBackground,
            textColor = colors.onBackground,
            onClick = {
                try {
                    // Open Play Store app
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=${context.packageName}")
                        )
                    )
                } catch (e: Exception) {
                    // If Play Store not available, open in browser
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=${context.packageName}")
                        )
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(40.dp))

        IconText(
            icon = Icons.Default.Share,
            text = "Share App",
            iconTint = colors.onBackground,
            textColor = colors.onBackground,
            onClick = {
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "Check out my app Eventra! 🎉\nDownload now: https://play.google.com/store/apps/details?id=${context.packageName}"
                    )
                }
                context.startActivity(Intent.createChooser(shareIntent, "Share via"))
            }
        )

        Spacer(modifier = Modifier.height(40.dp))

        IconText(
            icon = Icons.Default.BrandingWatermark,
            text = "Terms and Conditions",
            iconTint = colors.onBackground,
            textColor = colors.onBackground,
            onClick = {
                navController.navigate(Screen.TermsAndConditionScreen.route)
            }
        )

        Spacer(modifier = Modifier.height(40.dp))

        IconText(
            icon = Icons.Default.Cookie,
            text = "Cookies Policy",
            iconTint = colors.onBackground,
            textColor = colors.onBackground
        )

        Spacer(modifier = Modifier.height(40.dp))

        IconText(
            icon = Icons.Default.Feedback,
            text = "Feedback",
            iconTint = colors.onBackground,
            textColor = colors.onBackground
        )

        Spacer(modifier = Modifier.height(40.dp))

        IconText(
            icon = Icons.Default.Logout,
            text = "Logout",
            iconTint = colors.onBackground,
            textColor = colors.onBackground,
            onClick = {
                signInViewmodel.logOut()
                navController.navigate(Screen.SignInScreen.route)
            }
        )
    }
}

@Composable


fun IconText(
    text: String,
    icon: ImageVector,
    iconTint: Color,
    textColor: Color,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }, // 🔥 Full row clickable
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = iconTint)
        Spacer(modifier = Modifier.width(30.dp))
        Text(text = text, fontFamily = myFont, fontSize = 18.sp, color = textColor)
    }
}