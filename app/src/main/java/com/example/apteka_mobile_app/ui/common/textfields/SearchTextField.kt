package com.example.apteka_mobile_app.ui.common.textfields

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apteka_mobile_app.R
import com.example.apteka_mobile_app.ui.theme.poppins

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    enabled: Boolean = true,
    onValueChanged: (String) -> Unit = {},
    onCancelSearch: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        val searchCancelBtnVisibility = remember(value) {
            derivedStateOf { value.isNotEmpty() }
        }

        BasicTextField(
            modifier = Modifier.weight(1f),
            value = value,
            onValueChange = onValueChanged,
            enabled = enabled,
            singleLine = true,
            textStyle = TextStyle(
                color = Color.Black,
                fontFamily = poppins,
                fontSize = 16.sp
            )
        ) { innerTextField ->
            Row(
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(horizontal = 17.dp, vertical = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(17.dp))

                Box {
                    if (value.isEmpty()) {
                        Text(
                            text = "Поиск лекарств",
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

        AnimatedVisibility(
            modifier = Modifier.animateContentSize(animationSpec = spring()),
            visible = searchCancelBtnVisibility.value,
            enter = expandHorizontally(),
            exit = shrinkHorizontally()
        ) {
            Spacer(modifier = Modifier.width(10.dp))

            TextButton(onClick = onCancelSearch) {
                Text(
                    text = "Отмена",
                    style = TextStyle(
                        color = Color(0xFF1377FE),
                        fontFamily = poppins,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun SearchTextFieldPreview() {

    var value by remember {
        mutableStateOf("")
    }

    SearchTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        value = value,
        onValueChanged = { value = it },
        onCancelSearch = { value = "" }
    )
}