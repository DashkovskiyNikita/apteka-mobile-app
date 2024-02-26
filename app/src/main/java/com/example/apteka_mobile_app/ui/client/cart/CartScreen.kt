package com.example.apteka_mobile_app.ui.client.cart

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apteka_mobile_app.R
import com.example.apteka_mobile_app.ui.client.products.ProductItem
import com.example.apteka_mobile_app.ui.theme.poppins
import org.koin.androidx.compose.getViewModel

@Composable
fun CartScreen(
    cartViewModel: CartViewModel = getViewModel()
) {
    val products by cartViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF8F8F8))
    ) {
        Crossfade(
            modifier = Modifier.weight(1f),
            targetState = products,
            label = ""
        ) { state ->
            if (state.isNotEmpty()) {
                LazyColumn(
                    contentPadding = PaddingValues(all = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(19.dp)
                ) {
                    items(
                        items = products,
                        key = { it.product.id }
                    ) { cartModel ->
                        ProductItem(
                            modifier = Modifier.fillMaxWidth(),
                            name = cartModel.product.name,
                            description = cartModel.product.description,
                            productsAmount = cartModel.amount,
                            price = cartModel.product.price.toPlainString(),
                            increaseProductsAmount = {
                                cartViewModel.increaseProductAmount(cartModel)
                            },
                            decreaseProductsAmount = {
                                cartViewModel.decreaseProductAmount(cartModel)
                            }
                        )
                    }
                }
            } else {
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
            }
        }

        if (products.isNotEmpty()) {
            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 20.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00A651)
                ),
                contentPadding = PaddingValues(10.dp),
                onClick = cartViewModel::createOrder
            ) {

                Text(
                    text = "Заказать",
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

@Composable
fun OrderItem(
    modifier: Modifier = Modifier,
    productName: String = "",
    amount: String = "",
    fullPrice: String = ""
) {
    Column(
        modifier = modifier
            .background(
                color = Color(0xFFF8F8F8),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 20.dp, vertical = 13.dp)
    ) {
        Text(
            text = productName,
            style = TextStyle(
                color = Color(0xFF0A0A0A),
                fontWeight = FontWeight.Medium,
                fontFamily = poppins,
                fontSize = 16.sp
            )
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            Text(
                text = fullPrice,
                style = TextStyle(
                    color = Color(0xFFFF1A1A),
                    fontFamily = poppins,
                    fontSize = 12.sp
                )
            )

            Divider(
                modifier = Modifier
                    .height(15.dp)
                    .width(1.dp),
                thickness = 1.dp,
                color = Color.Black.copy(alpha = 0.2f)
            )

            Text(
                text = "$amount штук(и)",
                style = TextStyle(
                    color = Color(0xFF757373),
                    fontFamily = poppins,
                    fontSize = 12.sp
                )
            )
        }
    }

}