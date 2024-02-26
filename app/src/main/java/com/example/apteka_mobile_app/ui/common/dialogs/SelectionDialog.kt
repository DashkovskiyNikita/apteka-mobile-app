package com.example.apteka_mobile_app.ui.common.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apteka_mobile_app.ui.theme.poppins

@Composable
fun <T> SelectionDialog(
    modifier: Modifier = Modifier,
    itemsList: List<T> = emptyList(),
    itemContent: @Composable (T) -> Unit = {}
) {
    LazyColumn(modifier = modifier) {
        items(itemsList) { item ->
            itemContent(item)
        }
    }
}

@Composable
fun SelectionDialogItem(
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(54.dp)
            .clickable(role = Role.Button, onClick = onClick)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Start,
            style = TextStyle(
                color = Color(0xFF4D4C4C),
                fontFamily = poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        )
    }

}


