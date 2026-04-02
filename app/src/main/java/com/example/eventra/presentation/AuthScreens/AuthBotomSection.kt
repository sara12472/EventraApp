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
    icons: List<Int>,
    onIconClick: (Int) -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {

        // 🔹 Divider + Text
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Divider(modifier = Modifier.weight(1f), color = Color.Gray)

            Text(
                text = "  Sign in with  ",
                color = Color.Gray,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp
            )

            Divider(modifier = Modifier.weight(1f), color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(20.dp)) // ✅ reduced

        // 🔹 Icons (responsive spacing)
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth(0.7f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            icons.forEach { icon ->
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(36.dp)
                        .clickable { onIconClick(icon) },
                    tint = Color.Unspecified
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp)) // ✅ reduced

        // 🔹 Bottom Text (FIXED 🔥)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = bottomText,
                fontSize = 13.sp,
                color = Color.Gray,
                maxLines = 1
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = clickableText,
                color = mainColor,
                fontSize = 13.sp,
                maxLines = 1,
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