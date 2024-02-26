package com.example.apteka_mobile_app.ui.common.textfields

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apteka_mobile_app.ui.theme.poppins

@Composable
fun BaseTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    readOnly: Boolean = false,
    enabled: Boolean = true,
    placeholder: String = "",
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChanged: (String) -> Unit = {}
) {
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChanged,
        enabled = enabled,
        readOnly = readOnly,
        singleLine = singleLine,
        textStyle = TextStyle(
            color = Color.Black,
            fontFamily = poppins,
            fontSize = 16.sp
        ),
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        keyboardActions = keyboardActions
    ) { innerTextField ->
        Box(
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 17.dp, vertical = 15.dp),
        ) {
            if (value.isEmpty()) {
                Text(
                    text = placeholder,
                    style = TextStyle(
                        color = Color(0xFFABAFB3),
                        fontFamily = poppins,
                        fontSize = 16.sp
                    )
                )
            }
            innerTextField()
        }
    }
}

@Preview
@Composable
fun BaseTextFieldPreview() {
    var value by remember {
        mutableStateOf("")
    }
    BaseTextField(
        value = value,
        placeholder = "Логин или номер телефона",
        onValueChanged = { value = it }
    )
}