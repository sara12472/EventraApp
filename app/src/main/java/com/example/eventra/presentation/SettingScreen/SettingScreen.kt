package com.example.eventra.presentation.SettingScreen

import android.graphics.drawable.Icon
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
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventra.presentation.component.AppTopBar
import com.example.eventra.ui.theme.myFont
import java.nio.file.WatchEvent

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(top=50.dp, start=20.dp)) {
        AppTopBar(
            text = "Setting",
            onBackClick = {
            },
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(30.dp))
        IconText(icon = Icons.Default.NotificationsActive,
            text = "Notification"
            )
        Spacer(modifier = Modifier.height(25.dp))
        IconText(icon = Icons.Default.DarkMode,
            text = "Dark Mode"
        )
        Spacer(modifier = Modifier.height(25.dp))
        IconText(icon = Icons.Default.RateReview,
            text = "Rate App"
        )
        Spacer(modifier = Modifier.height(25.dp))
        IconText(icon = Icons.Default.Share,
            text = "Share App"
        )
        Spacer(modifier = Modifier.height(25.dp))
        IconText(icon = Icons.Default.BrandingWatermark,
            text = "Terms and Condition"
        )
        Spacer(modifier = Modifier.height(25.dp))
        IconText(icon = Icons.Default.Cookie,
            text = "Cookies Policy"
        )
        Spacer(modifier = Modifier.height(25.dp))
        IconText(icon = Icons.Default.Feedback,
            text = "Feedback"
        )
    }


}
@Composable
fun IconText(
    text: String,
    icon: ImageVector,
    onClick:() -> Unit={}

){
    Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){

        IconButton(
            onClick = {onClick},
        ) {
            Icon(
                imageVector =icon,
                contentDescription = null,
                tint =Color.Black
            )
        }

        Spacer(modifier = Modifier.width(30.dp))
        Text(text, fontFamily = myFont,
            fontSize = 18.sp,
            )



    }

}