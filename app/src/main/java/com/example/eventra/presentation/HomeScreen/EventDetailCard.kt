package com.example.eventra.presentation.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import com.example.eventra.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventra.ui.theme.AppGradient
import com.example.eventra.ui.theme.mainColor
import com.example.eventra.ui.theme.myFont
import kotlin.concurrent.timer

@Composable
fun EventDetailCard(
    title: String,
    date:String,
    time:String,
    location: String,
    repeat: String,
    reminder: String,
    detail: String,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color? = null,
    backgroundBrush: Brush? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            title,
            color = mainColor
        )

        Column {
            Text(date, color = mainColor, fontFamily = myFont, fontSize = 15.sp)
            Text(time, color = mainColor, fontFamily = myFont, fontSize = 15.sp)
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor ?: Color.Transparent
        )
    ) {
        Column(modifier = Modifier.fillMaxWidth().then(
            if (backgroundBrush != null)
                Modifier.background(backgroundBrush)
            else Modifier
        ).padding(20.dp)) {

            Text(text = "Title: $title", color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Location: $location", color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Repeat: $repeat", color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Reminder: $reminder", color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Detail: $detail", color = Color.White)
            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.End
            ){
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp),

                    ){
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = if (backgroundBrush != null) Color.White else mainColor,
                        modifier = Modifier.size(20.dp).clickable{onEditClick},
                    )
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Delete",
                        tint = if (backgroundBrush != null) Color.White else mainColor,
                        modifier = Modifier.size(20.dp).clickable{onDeleteClick}
                    )
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Share",
                        tint = if (backgroundBrush != null) Color.White else mainColor,
                        modifier = Modifier.size(20.dp).clickable{onShareClick}
                    )
                }
            }


            }
        }
    }

@Preview()
@Composable
fun ShowCardDetail(){
    EventDetailCard(
        title = "Online Flutter Class",
        location = "Zoom",
        date="4/5/6",
        time = "890",
        repeat = "Weekly",
        reminder = "15 minutes before",
        detail = "Live session covering Flutter layouts and state management basics.",
        onEditClick = {},
        onDeleteClick = {},
        onShareClick = {},
        backgroundBrush = AppGradient
    )

}