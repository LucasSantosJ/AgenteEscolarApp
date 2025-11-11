package com.br.agenteescolar.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.ui.graphics.vector.ImageVector
import com.br.agenteescolar.navigation.Routes

sealed class BottomNavBarItem(
    val label: String,
    val icon: ImageVector,
    val route: String,
) {
    object Alunos : BottomNavBarItem(
        label = "Alunos",
        icon = Icons.AutoMirrored.Filled.List,
        route = Routes.ALUNOS_LIST,
    )

    object Mapa : BottomNavBarItem(
        label = "Mapa",
        icon = Icons.Filled.Map,
        route = Routes.MAPA_GERAL,
    )

    object Perfil : BottomNavBarItem(
        label = "Perfil",
        icon = Icons.Outlined.AccountCircle,
        route = Routes.PERFIL,
    )
}


