package com.br.agenteescolar.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.br.agenteescolar.model.BottomNavBarItem

@Composable
fun AppBottomBar(navController: NavController) {

    val items = listOf(
        BottomNavBarItem.Alunos,
        BottomNavBarItem.Mapa,
        BottomNavBarItem.Visitas

    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                label = { Text(item.label) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {

                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}