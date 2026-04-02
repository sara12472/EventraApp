package com.example.eventra.presentation.Onboarding

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import com.example.eventra.ui.theme.mainColor


@Composable
fun OnboardingTopBar(
    showStartIcon: Boolean,
    image: ImageVector,
    iconColor: Color ,
    textButton: String,
    onBackClick: () -> Unit,
    onSkipClick: () -> Unit,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (showStartIcon) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = image,
                    contentDescription = "Back",
                    tint = iconColor
                )
            }
        } else {
            Spacer(modifier = Modifier.size(48.dp))
        }

        Text(
            text = textButton,
            modifier = Modifier.clickable { onSkipClick() },
            fontWeight = FontWeight.Medium,
            color = mainColor,
            fontSize = 15.sp
        )
    }

}
@Preview(showSystemUi = true)
@Composable
fun OnboardingTopBarPreview() {
    OnboardingTopBar(
                showStartIcon = true,
                image = Icons.Default.ArrowBack,
                iconColor = mainColor,
                textButton = "Skip",
                onBackClick = {},
                onSkipClick = {},
        modifier = Modifier.padding(top = 50.dp)
            )
        }
