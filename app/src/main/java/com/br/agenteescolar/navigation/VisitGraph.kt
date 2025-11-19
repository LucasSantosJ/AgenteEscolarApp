package com.br.agenteescolar.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.agenteescolar.MainActivity
import com.br.agenteescolar.screens.VisitScreen
import com.br.agenteescolar.viewmodel.VisitViewModel // <-- 2. Importa o SEU ViewModel

/**
 * Registra a rota da tela de Visitas no NavHost
 */
fun NavGraphBuilder.visitGraph(navController: NavController) {

    // 3. Registra a SUA rota (que criamos no Routes.kt)
    composable(Routes.VISITAS_LIST) {

        // Pega o contexto (que é a MainActivity)
        val activity = navController.context as MainActivity

        // 4. Cria o SEU ViewModel usando a Factory
        // A Factory vai ver que pedimos um VisitViewModel
        // e vai construir ele com o VisitaRepository
        val viewModel: VisitViewModel = viewModel(
            factory = activity.viewModelFactory
        )

        // 5. Chama a SUA tela Composablez
        VisitScreen(
            viewModel = viewModel
            // Como a VisitScreen não tem cliques de navegação,
            // não precisamos passar 'onAlunoClick' etc.
        )
    }
}
