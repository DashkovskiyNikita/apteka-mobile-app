package com.example.apteka_mobile_app.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.apteka_mobile_app.data.PreferenceManager
import com.example.apteka_mobile_app.data.api.UserType
import com.example.apteka_mobile_app.ui.client.cart.CartScreen
import com.example.apteka_mobile_app.ui.client.products.ProductsScreen
import com.example.apteka_mobile_app.ui.client.register.RegisterScreen
import com.example.apteka_mobile_app.ui.common.login.LoginScreen
import com.example.apteka_mobile_app.ui.common.profile.ProfileScreen
import com.example.apteka_mobile_app.ui.common.splash.SplashScreen
import com.example.apteka_mobile_app.ui.employee.deliveries.DeliveryScreen
import com.example.apteka_mobile_app.ui.employee.orders.OrdersScreen
import org.koin.compose.koinInject

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    preferenceManager: PreferenceManager = koinInject(),
) {
    val isUserAuthorized by preferenceManager.isUserAuthorizedFlow.collectAsState(initial = false)
    val userType by preferenceManager.userTypeFlow.collectAsState(initial = UserType.NONE)

    Scaffold(
        bottomBar = {
            val backStack by navController.currentBackStackEntryAsState()
            val currentRoute = backStack?.destination?.route ?: ""

            if (currentRoute !in Navigator.routesWithoutBottomBar) {
                BottomNavigationBar(
                    currentRoute = currentRoute,
                    isClientMode = userType == UserType.CLIENT,
                    navigateToRoute = { route ->
                        navController.navigate(route) {
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = Navigator.Splash.route
        ) {
            composable(Navigator.Splash.route) {
                SplashScreen(
                    navigateNext = {
                        val nextRoute = when {
                            !isUserAuthorized -> Navigator.Login.route
                            userType == UserType.CLIENT -> Navigator.Products.route
                            userType == UserType.EMPLOYEE -> Navigator.Orders.route
                            else -> Navigator.Login.route
                        }
                        navController.navigate(nextRoute) {
                            popUpTo(Navigator.Splash.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(route = Navigator.Login.route) {
                LoginScreen(
                    navigateToRegisterScreen = {
                        navController.navigate(Navigator.Register.route)
                    },
                    successLogin = {
                        val startRoute = if (userType == UserType.CLIENT)
                            Navigator.Products.route
                        else
                            Navigator.Orders.route

                        navController.navigate(startRoute) {
                            popUpTo(Navigator.Login.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable(route = Navigator.Register.route) {
                RegisterScreen(
                    navigateBack = {
                        navController.popBackStack()
                    }
                )
            }
            composable(route = Navigator.Products.route) {
                ProductsScreen()
            }
            composable(route = Navigator.Cart.route) {
                CartScreen()
            }
            composable(route = Navigator.Profile.route) {
                ProfileScreen(
                    navigateToLoginScreen = {
                        navController.navigate(Navigator.Login.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable(route = Navigator.Orders.route) {
                OrdersScreen()
            }
            composable(route = Navigator.Deliveries.route) {
                DeliveryScreen()
            }
        }
    }
}