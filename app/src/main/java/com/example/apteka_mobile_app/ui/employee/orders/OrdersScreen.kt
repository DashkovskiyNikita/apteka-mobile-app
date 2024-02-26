package com.example.apteka_mobile_app.ui.employee.orders

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apteka_mobile_app.R
import com.example.apteka_mobile_app.data.api.ClientOrderProductInfo
import com.example.apteka_mobile_app.ui.client.cart.OrderItem
import com.example.apteka_mobile_app.ui.theme.poppins
import org.koin.androidx.compose.getViewModel
import java.math.BigDecimal

@Composable
fun OrdersScreen(
    ordersViewModel: OrdersViewModel = getViewModel()
) {
    val state by ordersViewModel.uiState.collectAsState()

    Crossfade(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF8F8F8)),
        targetState = state.isLoading,
        label = ""
    ) { loadingState ->
        if (loadingState) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF00A651))
            }
        } else {
            if (state.orders.isEmpty()){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_apteka),
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "Пока нет заказов",
                        style = TextStyle(
                            color = Color(0xFF4D4C4C),
                            fontFamily = poppins,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 30.sp
                        )
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(vertical = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(19.dp)
                ) {
                    items(
                        items = state.orders,
                        key = { it.orderId }
                    ) { order ->
                        OrderCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            orderNumber = "Заказ № ${order.orderId}",
                            date = "", //todo
                            orderProducts = order.products,
                            totalPrice = order.totalPrice.toPlainString(),
                            onCancelOrderClicked = {
                                ordersViewModel.cancelOrder(order)
                            },
                            onIssueOrderClicked = {
                                ordersViewModel.issueOrder(order, it)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OrderCard(
    modifier: Modifier = Modifier,
    orderNumber: String = "",
    date: String = "",
    orderProducts: List<ClientOrderProductInfo> = emptyList(),
    totalPrice: String = "",
    onCancelOrderClicked: () -> Unit = {},
    onIssueOrderClicked: (Boolean) -> Unit = {}
) {
    Column(
        modifier = modifier.background(
            color = Color.White,
            shape = RoundedCornerShape(20.dp)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .padding(top = 29.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = orderNumber,
                style = TextStyle(
                    color = Color(0xFF4D4C4C),
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = poppins,
                    fontSize = 20.sp
                )
            )

            Text(
                text = date,
                style = TextStyle(
                    color = Color(0xFF4D4C4C),
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = poppins,
                    fontSize = 16.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        orderProducts.forEach { orderProduct ->
            key(orderProduct.productId) {
                OrderItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 19.dp),
                    productName = orderProduct.name,
                    amount = orderProduct.amount.toString(),
                    fullPrice = "${orderProduct.fullPrice.toPlainString()}₽"
                )
                Spacer(modifier = Modifier.height(17.dp))
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Итого",
                style = TextStyle(
                    color = Color(0xFF4D4C4C),
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = poppins,
                    fontSize = 16.sp
                )
            )

            Text(
                text = "$totalPrice₽",
                style = TextStyle(
                    color = Color(0xFFFF1A1A),
                    fontFamily = poppins,
                    fontSize = 16.sp
                )
            )
        }

        var withPrescription by remember {
            mutableStateOf(false)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(30.dp)
                .padding(horizontal = 25.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "По рецепту",
                color = Color.Black,
                textAlign = TextAlign.Start,
                style = TextStyle(
                    color = Color.Black,
                    fontFamily = poppins,
                    fontSize = 16.sp
                )
            )
            Checkbox(
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFF27AE60),
                    checkmarkColor = Color.White
                ),
                checked = withPrescription,
                onCheckedChange = {
                    withPrescription = !withPrescription
                }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00A651)
            ),
            contentPadding = PaddingValues(10.dp),
            onClick = {
                onIssueOrderClicked(withPrescription)
            }
        ) {

            Text(
                text = "Выдать",
                textAlign = TextAlign.Center,
                style = TextStyle(
                    color = Color.White,
                    fontFamily = poppins,
                    fontSize = 16.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 20.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00A651)
            ),
            contentPadding = PaddingValues(10.dp),
            onClick = onCancelOrderClicked
        ) {

            Text(
                text = "Отменить",
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
fun OrderCardPreview() {
    OrderCard(
        modifier = Modifier.padding(all = 20.dp),
        orderNumber = "Заказ № 12345",
        date = "10.10.23",
        orderProducts = listOf(
            ClientOrderProductInfo(
                productId = 0,
                name = "Лизобакт",
                fullPrice = BigDecimal("400"),
                amount = 2
            ),
            ClientOrderProductInfo(
                productId = 1,
                name = "Мирамистин",
                fullPrice = BigDecimal("500"),
                amount = 1
            ),
            ClientOrderProductInfo(
                productId = 2,
                name = "Парацетомол",
                fullPrice = BigDecimal("700"),
                amount = 3
            )
        ),
        totalPrice = "1600"
    )
}

