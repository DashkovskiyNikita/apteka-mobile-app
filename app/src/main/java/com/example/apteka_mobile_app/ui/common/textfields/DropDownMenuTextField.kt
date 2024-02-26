package com.example.apteka_mobile_app.ui.common.textfields

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> DropDownMenuTextField(
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    placeholder: String = "Выберите категорию",
    selectedText: String = "",
    dropDownMenuList: List<T> = emptyList(),
    dropDownMenuListItem: @Composable ColumnScope.(T) -> Unit = {},
    onExpandedChanged: (Boolean) -> Unit = {}
) {
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = onExpandedChanged
    ) {

        BaseTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            value = selectedText,
            readOnly = true,
            placeholder = placeholder
        )

        ExposedDropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = {
                onExpandedChanged(!expanded)
            }
        ) {
            dropDownMenuList.forEach { dropDownItem ->
                dropDownMenuListItem(dropDownItem)
            }
        }
    }
}

@Preview
@Composable
fun DropDownMenuTextFieldPreview() {

    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedText by remember {
        mutableStateOf("")
    }

    DropDownMenuTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 20.dp),
        expanded = expanded,
        selectedText = selectedText,
        dropDownMenuList = remember {
            listOf("1", "2", "3", "4")
        },
        dropDownMenuListItem = { item ->
            DropdownMenuItem(
                modifier = Modifier.fillMaxWidth(),
                text = { Text(text = item) },
                onClick = {
                    expanded = false
                    selectedText = item
                }
            )
        },
        onExpandedChanged = {
            expanded = it
        }
    )
}
