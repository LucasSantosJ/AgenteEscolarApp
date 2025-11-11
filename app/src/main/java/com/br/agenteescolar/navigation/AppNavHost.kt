package com.br.agenteescolar.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = {
            AppBottomBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.ALUNOS_LIST, // Define a tela inicial
            modifier = Modifier.padding(innerPadding)
        ) {
            // 1️⃣ Lista de Alunos
            listaAlunosScreen(navController)

            // 2️⃣ Mapa
            mapaGraph(navController)

            // 3️⃣ Perfil
            perfilGraph(navController)
        }
    }
}
