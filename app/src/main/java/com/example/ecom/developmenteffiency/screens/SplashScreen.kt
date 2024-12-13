package com.example.ecom.developmenteffiency.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecom.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    moveToLogin:()->Unit,
    ) {
    var style = TextStyle(
        fontSize = 45.sp,
        fontFamily = FontFamily(Font(R.raw.iceberg_regular)),
        color = Color.Magenta
    )
    LaunchedEffect(true) {
        delay(3000)
        moveToLogin()
    }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Compose",
            style = style
        )
        Text(
            text = "VS",
            style = style
        )
        Text(
            text = "XML",
            style = style
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Compose Version",
            fontSize = 18.sp,
        )
    }
}