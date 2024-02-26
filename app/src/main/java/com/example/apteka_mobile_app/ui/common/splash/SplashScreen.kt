package com.example.apteka_mobile_app.ui.common.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apteka_mobile_app.R
import com.example.apteka_mobile_app.ui.theme.poppins
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navigateNext: () -> Unit = {}) {

    LaunchedEffect(Unit){
        delay(3000)
        navigateNext()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF8F8F8)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_apteka),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Аптека",
            style = TextStyle(
                color = Color(0xFF4D4C4C),
                fontFamily = poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 30.sp
            )
        )

        Text(
            text = "Здоровье прежде всего",
            style = TextStyle(
                color = Color(0xFF908F8F),
                fontFamily = poppins,
                fontSize = 14.sp
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        CircularProgressIndicator(color = Color(0xFF00A651))
    }
}