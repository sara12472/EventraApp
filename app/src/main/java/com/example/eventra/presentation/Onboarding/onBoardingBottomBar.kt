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
pageCount:Int=3,
currentPage: Int,
onNextClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            repeat(pageCount) { index ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(10.dp)
                        .background(
                            color = if (index == currentPage) mainColor else Color.Gray
                        )
                )

            }
        }

        AppButton(text = if (currentPage == pageCount-1) "GET STARTED" else "NEXT" , onClick = onNextClick, modifier = Modifier.width(190.dp).height(47.dp))
    }
}
@Preview()
@Composable
fun BottomBarPreview(){
    val currentPage= remember { mutableStateOf(0) }
    OnBoardingBottomBar(
        currentPage=currentPage.value,
        onNextClick = {currentPage.value = (currentPage.value + 1) % 3}
    )
}