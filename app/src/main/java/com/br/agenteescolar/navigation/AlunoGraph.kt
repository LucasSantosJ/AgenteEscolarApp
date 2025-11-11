package com.br.agenteescolar.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.agenteescolar.MainActivity
import com.br.agenteescolar.screens.ListaAlunosScreen
import com.br.agenteescolar.viewmodel.ListaAlunosViewModel

fun NavGraphBuilder.listaAlunosScreen(navController: NavController) {

    // Registra a rota (usando o nome de Routes.kt)
    composable(Routes.ALUNOS_LIST) {

        // Pega o contexto (que é a MainActivity)
        val activity = navController.context as MainActivity

        // Cria o ViewModel usando a Factory da MainActivity
        val viewModel: ListaAlunosViewModel = viewModel(
            factory = activity.viewModelFactory
        )

        // Chama a tela Composable
        ListaAlunosScreen(
            viewModel = viewModel,
            onAlunoClick = { aluno ->
                // TODO: Navegar para os detalhes do aluno
                // navController.navigate(...)
            },
            onAdicionarAlunoClick = {
                // TODO: Navegar para a tela de adicionar aluno
            }
        )
    }
}