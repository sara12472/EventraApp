package com.example.eventra.presentation.Onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.eventra.presentation.component.AppButton
import com.example.eventra.ui.theme.mainColor

@Composable
fun OnBoardingBottomBar(
    pageCount: Int = 3,
    currentPage: Int,
    onNextClick: () -> Unit,
    modifier: Modifier
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        // 🔵 DOTS (responsive wrap)
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(pageCount) { index ->
                Box(
                    modifier = Modifier
                        .padding(3.dp)
                        .size(if (index == currentPage) 10.dp else 8.dp)
                        .background(
                            color = if (index == currentPage) mainColor else Color.LightGray
                        )
                )
            }
        }

        // 🔘 BUTTON (responsive width)
        AppButton(
            text = if (currentPage == pageCount - 1) "GET STARTED" else "NEXT",
            onClick = onNextClick,
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth(0.55f)   // 👈 responsive width fix
        )
    }
}
@Preview()
@Composable
fun BottomBarPreview(){


}