package com.br.agenteescolar.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.agenteescolar.screens.MapaScreen

fun NavGraphBuilder.mapaGraph(navController: NavController) {
    composable(Routes.MAPA_GERAL) {
        MapaScreen()
    }
}