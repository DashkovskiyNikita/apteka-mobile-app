package com.example.apteka_mobile_app.ui.employee.deliveries

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apteka_mobile_app.ui.common.dialogs.SelectionDialog
import com.example.apteka_mobile_app.ui.common.dialogs.SelectionDialogItem
import com.example.apteka_mobile_app.ui.common.textfields.BaseTextField
import com.example.apteka_mobile_app.ui.theme.poppins
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryScreen(
    deliveryViewModel: DeliveryViewModel = getViewModel()
) {
    val state by deliveryViewModel.uiState.collectAsState()

    val suppliersBottomSheetState = rememberModalBottomSheetState(true)
    val vendorCodesBottomSheetState = rememberModalBottomSheetState(true)

    val scope = rememberCoroutineScope()

    if (suppliersBottomSheetState.isVisible) {
        ModalBottomSheet(
            modifier = Modifier
                .heightIn(max = 500.dp)
                .padding(all = 10.dp),
            containerColor = Color.White,
            onDismissRequest = {
                scope.launch { suppliersBottomSheetState.hide() }
            }
        ) {
            SelectionDialog(
                itemsList = state.suppliersList,
                itemContent = { item ->
                    SelectionDialogItem(
                        text = item.name,
                        onClick = {
                            deliveryViewModel.setSupplier(item)
                            scope.launch { suppliersBottomSheetState.hide() }
                        }
                    )
                }
            )
        }
    }

    if (vendorCodesBottomSheetState.isVisible) {
        ModalBottomSheet(
            modifier = Modifier
                .heightIn(max = 500.dp)
                .padding(all = 10.dp),
            containerColor = Color.White,
            onDismissRequest = {
                scope.launch { vendorCodesBottomSheetState.hide() }
            }
        ) {
            SelectionDialog(
                itemsList = state.vendorCodesList,
                itemContent = { item ->
                    SelectionDialogItem(
                        text = item,
                        onClick = {
                            deliveryViewModel.setVendorCode(item)
                            scope.launch { vendorCodesBottomSheetState.hide() }
                        }
                    )
                }
            )
        }
    }

    Crossfade(
        targetState = state.isLoading,
        label = ""
    ) { targetState ->
        if (targetState) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF00A651))
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0xFFF8F8F8)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                BaseTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .onFocusChanged { focusState ->
                            if (focusState.isFocused) {
                                scope.launch { suppliersBottomSheetState.show() }
                            }
                        },
                    value = state.selectedSupplier.name,
                    readOnly = true,
                    placeholder = "Поставщик"
                )

                Spacer(modifier = Modifier.height(20.dp))

                BaseTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .onFocusChanged { focusState ->
                            if (focusState.isFocused) {
                                scope.launch { vendorCodesBottomSheetState.show() }
                            }
                        },
                    value = state.deliveryProduct.vendorCode,
                    readOnly = true,
                    placeholder = "Артикул"
                )

                Spacer(modifier = Modifier.height(20.dp))

                BaseTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    value = state.editAmount,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeholder = "Количество",
                    onValueChanged = deliveryViewModel::setAmount
                )

                Spacer(modifier = Modifier.height(20.dp))

                BaseTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    value = state.editPriceValue,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeholder = "Цена",
                    onValueChanged = deliveryViewModel::setPrice
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00A651)
                    ),
                    contentPadding = PaddingValues(10.dp),
                    onClick = deliveryViewModel::addProductToDelivery
                ) {

                    Text(
                        text = "Добавить товар",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = Color.White,
                            fontFamily = poppins,
                            fontSize = 16.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    items(
                        items = state.deliveryProducts,
                        key = { it.vendorCode }
                    ) { product ->
                        DeliveryProductItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            vendorCode = product.vendorCode,
                            amount = product.amount,
                            price = product.price.toPlainString(),
                            onDeleteClick = {
                                deliveryViewModel.removeDeliveryProduct(product)
                            }
                        )
                    }
                }

                if (state.deliveryProducts.isNotEmpty()) {
                    TextButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .padding(bottom = 20.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00A651)
                        ),
                        contentPadding = PaddingValues(10.dp),
                        onClick = deliveryViewModel::newDelivery
                    ) {
                        Text(
                            text = "Сохранить поставку",
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
        }
    }
}

@Composable
fun DeliveryProductItem(
    modifier: Modifier = Modifier,
    vendorCode: String = "",
    amount: Int = 0,
    price: String = "",
    onDeleteClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 15.dp, vertical = 13.dp)
    ) {
        Text(
            text = "Артикул $vendorCode",
            style = TextStyle(
                color = Color(0xFF0A0A0A),
                fontWeight = FontWeight.Medium,
                fontFamily = poppins,
                fontSize = 16.sp
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Количество $amount",
            style = TextStyle(
                color = Color(0xFFABAFB3),
                fontFamily = poppins,
                fontSize = 12.sp
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "$price₽",
            style = TextStyle(
                color = Color(0xFFFF1A1A),
                fontFamily = poppins,
                fontSize = 16.sp
            )
        )

        TextButton(
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF8F8F8)
            ),
            onClick = onDeleteClick
        ) {
            Text(
                text = "Удалить",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = Color(0xFF27AE60),
                    fontFamily = poppins,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            )
        }
    }
}