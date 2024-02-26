package com.example.apteka_mobile_app.ui.common.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apteka_mobile_app.R
import com.example.apteka_mobile_app.ui.common.textfields.BaseTextField
import com.example.apteka_mobile_app.ui.theme.poppins
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginScreen(
    successLogin: () -> Unit = {},
    navigateToRegisterScreen: () -> Unit = {},
    loginViewModel: LoginViewModel = getViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF8F8F8)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(200.dp))

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


        Spacer(modifier = Modifier.height(20.dp))

        val state by loginViewModel.uiState.collectAsState()

        if (state.loginError) {
            Toast.makeText(LocalContext.current, "Неверный логин или пароль", Toast.LENGTH_SHORT).show()
            loginViewModel.resetLoginError()
        }

        if(state.loginSuccess){
            successLogin()
            loginViewModel.resetLoginSuccess()
        }

        BaseTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            value = state.login,
            placeholder = "Логин или номер телефона",
            onValueChanged = loginViewModel::setLogin
        )

        Spacer(modifier = Modifier.height(20.dp))

        BaseTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            value = state.password,
            placeholder = "Пароль",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = remember { PasswordVisualTransformation() },
            onValueChanged = loginViewModel::setPassword
        )

        Spacer(modifier = Modifier.height(70.dp))

        TextButton(
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00A651)
            ),
            contentPadding = PaddingValues(10.dp),
            onClick = loginViewModel::tryLogin
        ) {

            Text(
                modifier = Modifier.width(160.dp),
                text = "Войти",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = Color.White,
                    fontFamily = poppins,
                    fontSize = 16.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        TextButton(
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00A651)
            ),
            contentPadding = PaddingValues(10.dp),
            onClick = navigateToRegisterScreen
        ) {

            Text(
                modifier = Modifier.width(160.dp),
                text = "Зарегистрироваться",
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

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}