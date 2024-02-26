package com.example.apteka_mobile_app.ui.client.products

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apteka_mobile_app.R
import com.example.apteka_mobile_app.data.api.Category
import com.example.apteka_mobile_app.ui.common.textfields.SearchTextField
import com.example.apteka_mobile_app.ui.theme.poppins
import org.koin.androidx.compose.getViewModel

@Composable
fun ProductsScreen(
    productsViewModel: ProductsViewModel = getViewModel()
) {

    val state by productsViewModel.uiState.collectAsState()

    LaunchedEffect(productsViewModel) {
        productsViewModel.getProductsAndCategories()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF8F8F8))
    ) {

        SearchTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 20.dp),
            value = state.search,
            onValueChanged = productsViewModel::setSearch,
            onCancelSearch = productsViewModel::resetSearchByName
        )

        Spacer(modifier = Modifier.height(20.dp))

        SearchByCategories(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            categories = state.categories,
            selectedCategories = state.selectedCategoriesId,
            onCategorySelected = { category, selected ->
                productsViewModel.setSearchCategory(category.categoryId, selected)
            },
            onSearchButtonClicked = {
                productsViewModel.getProductsByCategories()
            },
            onResetSearchButtonClicked = {
                productsViewModel.resetSearchByCategories()
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Crossfade(targetState = state.loading, label = "") { loadingState ->
            if (loadingState) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF00A651))
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(19.dp)
                ) {
                    items(
                        items = state.searchedProducts,
                        key = { it.product.id }
                    ) { cartModel ->
                        ProductItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            name = cartModel.product.name,
                            description = cartModel.product.description,
                            productsAmount = cartModel.amount,
                            price = cartModel.product.price.toPlainString(),
                            increaseProductsAmount = {
                                productsViewModel.increaseProductAmount(cartModel)
                            },
                            decreaseProductsAmount = {
                                productsViewModel.decreaseProductAmount(cartModel)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchByCategories(
    modifier: Modifier = Modifier,
    categories: List<Category> = emptyList(),
    selectedCategories: List<Int> = emptyList(),
    onCategorySelected: (Category, Boolean) -> Unit = { _, _ -> },
    onSearchButtonClicked: () -> Unit = {},
    onResetSearchButtonClicked: () -> Unit = {}
) {

    var searchByCategoriesOpened by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .heightIn(max = 500.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color = Color.White)
            .animateContentSize(animationSpec = spring())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(54.dp)
                .clickable(
                    role = Role.DropdownList
                ) {
                    searchByCategoriesOpened = !searchByCategoriesOpened
                },
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 15.dp),
                text = "Поиск по категориям",
                color = Color.Black,
                textAlign = TextAlign.Start,
                style = TextStyle(
                    color = Color.Black,
                    fontFamily = poppins,
                    fontSize = 16.sp
                )
            )
        }

        if (searchByCategoriesOpened) {
            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .padding(top = 20.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00A651)
                ),
                contentPadding = PaddingValues(10.dp),
                onClick = {
                    searchByCategoriesOpened = false
                    onSearchButtonClicked()
                }
            ) {

                Text(
                    text = "Поиск",
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        color = Color.White,
                        fontFamily = poppins,
                        fontSize = 16.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            TextButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00A651)
                ),
                contentPadding = PaddingValues(10.dp),
                onClick = onResetSearchButtonClicked
            ) {

                Text(
                    text = "Сбросить",
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
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(
                    items = categories,
                    key = { it.categoryId }
                ) { category ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .requiredHeight(30.dp)
                            .padding(horizontal = 15.dp),
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = category.name,
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
                            checked = category.categoryId in selectedCategories,
                            onCheckedChange = {
                                onCategorySelected(category, it)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    name: String = "",
    description: String = "",
    productsAmount: Int = 0,
    price: String = "",
    increaseProductsAmount: () -> Unit = {},
    decreaseProductsAmount: () -> Unit = {}
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
            text = name,
            style = TextStyle(
                color = Color(0xFF0A0A0A),
                fontWeight = FontWeight.Medium,
                fontFamily = poppins,
                fontSize = 16.sp
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = description,
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

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.End
        ) {
            if (productsAmount > 0) {
                ProductsAmountSelector(
                    amount = productsAmount.toString(),
                    increaseAmount = increaseProductsAmount,
                    decreaseAmount = decreaseProductsAmount
                )
            } else {
                AddToCartButton(onClick = increaseProductsAmount)
            }
        }
    }
}

@Composable
fun AddToCartButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    TextButton(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFF8F8F8)
        ),
        onClick = onClick
    ) {
        Text(
            text = "В корзину",
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

@Composable
fun ProductsAmountSelector(
    modifier: Modifier = Modifier,
    amount: String = "",
    increaseAmount: () -> Unit = {},
    decreaseAmount: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(Color(0xFFF8F8F8))
                .clickable(
                    role = Role.Button,
                    onClick = decreaseAmount
                ),
            painter = painterResource(R.drawable.ic_remove),
            tint = Color(0xFF27AE60),
            contentDescription = null
        )

        Text(
            modifier = Modifier.widthIn(min = 20.dp),
            text = amount,
            textAlign = TextAlign.Center,
            style = TextStyle(
                color = Color.Black,
                fontFamily = poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
        )

        Icon(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(Color(0xFFF8F8F8))
                .clickable(
                    role = Role.Button,
                    onClick = increaseAmount
                ),
            painter = painterResource(R.drawable.ic_add),
            tint = Color(0xFF27AE60),
            contentDescription = null
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProductsAmountSelectorPreview() {
    var amount by remember {
        mutableIntStateOf(0)
    }
    ProductsAmountSelector(
        amount = amount.toString(),
        increaseAmount = { amount++ },
        decreaseAmount = { if (amount > 0) amount-- }
    )
}

@Preview
@Composable
fun ProductItemPreview() {

    var amount by remember {
        mutableIntStateOf(0)
    }

    ProductItem(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        name = "Парацетомол",
        description = "Анальгетик и антипиретик",
        productsAmount = amount,
        increaseProductsAmount = { amount++ },
        decreaseProductsAmount = { if (amount > 0) amount-- }
    )
}