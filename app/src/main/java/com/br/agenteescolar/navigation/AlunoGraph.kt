package com.br.agenteescolar.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.agenteescolar.MainActivity
import com.br.agenteescolar.ui.screens.lista_alunos.ListaAlunosScreen
import com.br.agenteescolar.viewmodel.ListaAlunosViewModel

fun NavGraphBuilder.listaAlunosScreen(navController: NavController) {


    composable(Routes.ALUNOS_LIST) {


        val activity = navController.context as MainActivity


        val viewModel: ListaAlunosViewModel = viewModel(
            factory = activity.viewModelFactory
        )


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