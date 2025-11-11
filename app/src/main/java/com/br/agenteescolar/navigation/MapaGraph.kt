package com.br.agenteescolar.navigation

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

// Arquivos de placeholder para as outras telas

fun NavGraphBuilder.mapaGraph(navController: NavController) {
    composable(Routes.MAPA_GERAL) {
        // TODO: Substituir pela tela de Mapa
        Text("Tela de Mapa (WIP)")
    }
}

fun NavGraphBuilder.perfilGraph(navController: NavController) {
    composable(Routes.PERFIL) {
        // TODO: Substituir pela tela de Perfil
        Text("Tela de Perfil (WIP)")
    }
}