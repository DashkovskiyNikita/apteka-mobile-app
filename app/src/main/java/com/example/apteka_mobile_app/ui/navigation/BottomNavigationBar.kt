package com.example.apteka_mobile_app.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource

@Composable
fun BottomNavigationBar(
    currentRoute: String,
    isClientMode: Boolean = true,
    navigateToRoute: (String) -> Unit
) {

    val bottomBarItems = remember(isClientMode) {
        if (isClientMode)
            BottomBarItems.getClientItems()
        else
            BottomBarItems.getEmployeeItems()
    }

    NavigationBar {
        bottomBarItems.forEach { bottomBarItem ->
            NavigationBarItem(
                selected = currentRoute == bottomBarItem.destination,
                onClick = { navigateToRoute(bottomBarItem.destination) },
                icon = {
                    Icon(
                        painter = painterResource(bottomBarItem.icon),
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = bottomBarItem.title)
                }
            )
        }
    }
}