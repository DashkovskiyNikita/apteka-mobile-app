package com.example.apteka_mobile_app.ui.common.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apteka_mobile_app.ui.theme.poppins
import org.koin.androidx.compose.getViewModel

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = getViewModel(),
    navigateToLoginScreen: () -> Unit = {}
) {

    val state by profileViewModel.uiState.collectAsState()

    if (state.navigateToLoginScreen) {
        navigateToLoginScreen()
        profileViewModel.resetNavigateToLoginScreen()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(88.dp)
                .background(
                    color = Color(0xFFC4C4C4),
                    shape = CircleShape
                )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = state.fullName,
            style = TextStyle(
                color = Color(0xFF4D4C4C),
                fontFamily = poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
        )

        Text(
            text = state.phone,
            style = TextStyle(
                color = Color(0xFF908F8F),
                fontFamily = poppins,
                fontSize = 12.sp
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00A651)
            ),
            contentPadding = PaddingValues(10.dp),
            onClick = profileViewModel::logout
        ) {

            Text(
                modifier = Modifier.width(160.dp),
                text = "Выйти",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = Color.White,
                    fontFamily = poppins,
                    fontSize = 16.sp
                )
            )
        }
    }

}