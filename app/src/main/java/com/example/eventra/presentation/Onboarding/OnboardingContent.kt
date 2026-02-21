package com.example.eventra.presentation.Onboarding

import android.media.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventra.ui.theme.mainColor
import com.example.eventra.ui.theme.myFont
import com.example.eventra.R

@Composable
fun OnboardingContent(
    image: Int,
    tittle: String,
    description: String,
    modifier: Modifier,
) {
    Column(modifier = Modifier.padding(horizontal = 24.dp,),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        androidx.compose.foundation.Image(
            painter = painterResource(id=image),
            modifier = Modifier.height(290.dp).width(290.dp),
            contentDescription = null,

        )
        Spacer(modifier = Modifier.height(50.dp))
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
            Text(text = tittle,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = myFont,
                color = mainColor
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = description,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = myFont,
                color = Color.Black
            )
        }


    }


}
@Preview()
@Composable
fun OnboardingCenterContentPreview() {
    val item=onboardingItem[0]
    OnboardingContent(
        image = item.image,
        tittle = item.tittle,
        description = item.description,
        modifier = Modifier
    )
}