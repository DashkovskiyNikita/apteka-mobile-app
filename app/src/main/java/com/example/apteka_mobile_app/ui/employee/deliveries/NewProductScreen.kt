package com.example.apteka_mobile_app.ui.employee.deliveries

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.apteka_mobile_app.ui.common.textfields.BaseTextField

@Composable
fun NewProductScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF8F8F8)),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(20.dp))

        BaseTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            value = "",
            placeholder = "Наименование товара",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = remember { PasswordVisualTransformation() },
            onValueChanged = {

            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        BaseTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            value = "",
            placeholder = "Артикул",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = remember { PasswordVisualTransformation() },
            onValueChanged = {

            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        BaseTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            value = "",
            placeholder = "Категория",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = remember { PasswordVisualTransformation() },
            onValueChanged = {}
        )

        Spacer(modifier = Modifier.height(20.dp))

        BaseTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            value = "",
            placeholder = "Цена",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = remember { PasswordVisualTransformation() },
            onValueChanged = {}
        )

        Spacer(modifier = Modifier.height(20.dp))

        BaseTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            value = "",
            placeholder = "Описание",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = remember { PasswordVisualTransformation() },
            onValueChanged = {}
        )
    }
}