package com.example.eventra.presentation.AuthScreens
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventra.ui.theme.mainColor
import com.example.eventra.R


@Composable
fun AuthBottomSection(
    bottomText: String,
    clickableText: String,
    onClick: () -> Unit,
    icons: List<Int>,   // icons parameter
    onIconClick: (Int) -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
    ) {
        // "Sign in with" text and divider
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
        ) {
            Divider(modifier = Modifier.weight(1f), color = Color.Gray)
            Text(
                text = "  Sign in with  ",
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )
            Divider(modifier = Modifier.weight(1f), color = Color.Gray)
        }
        Spacer(modifier = Modifier.height(40.dp))

        // Social login icons
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icons.forEach { icon ->

                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { onIconClick(icon) },
                    tint = Color.Unspecified
                )

            }
        }

        Spacer(modifier = Modifier.height(40.dp))
        // Bottom clickable text
        Row {
            Text(text = bottomText,
                fontSize = 16.sp,
                color = Color.Gray)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = clickableText,
                color = mainColor, // your primary color
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                modifier = Modifier.clickable { onClick() }
            )
        }
    }
}

@Preview
@Composable
fun Preview() {
    AuthBottomSection(
        bottomText = "Don't have an account",
        clickableText = "sign up",
        onClick = {},
        icons =  listOf(
            R.drawable.google,
            R.drawable.facebook,
            R.drawable.apple
        )
        )
}