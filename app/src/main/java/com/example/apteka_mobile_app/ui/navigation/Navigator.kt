package com.example.apteka_mobile_app.ui.navigation

import androidx.annotation.DrawableRes
import com.example.apteka_mobile_app.R

sealed class Navigator(val route: String) {
    data object Splash: Navigator("splash")
    data object Login : Navigator("login")
    data object Register : Navigator("register")
    data object Products : Navigator("products")
    data object Cart : Navigator("cart")
    data object Profile : Navigator("profile")

    data object Orders : Navigator("orders")
    data object Deliveries : Navigator("deliveries")
    data object Reports : Navigator("reports")

    companion object {
        val routesWithoutBottomBar = listOf(Splash.route,Login.route, Register.route)
    }
}

enum class BottomBarItems(
    @DrawableRes val icon: Int,
    val title: String,
    val destination: String
) {
    PRODUCTS(
        icon = R.drawable.ic_home,
        title = "Товары",
        destination = Navigator.Products.route
    ),
    CART(
        icon = R.drawable.ic_shopping_cart,
        title = "Корзина",
        destination = Navigator.Cart.route
    ),
    PROFILE(
        icon = R.drawable.ic_profile,
        title = "Профиль",
        destination = Navigator.Profile.route
    ),
    ORDERS(
        icon = R.drawable.ic_orders,
        title = "Заказы",
        destination = Navigator.Orders.route
    ),
    DELIVERIES(
        icon = R.drawable.ic_delivery,
        title = "Поставка",
        destination = Navigator.Deliveries.route
    ),
    REPORTS(
        icon = R.drawable.ic_report,
        title = "Отчеты",
        destination = Navigator.Reports.route
    );

    companion object {

        fun getClientItems() = listOf(PRODUCTS, CART, PROFILE)

        fun getEmployeeItems() = listOf(ORDERS, DELIVERIES, PROFILE)

    }
}