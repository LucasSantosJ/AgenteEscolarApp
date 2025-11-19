package com.br.agenteescolar.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.agenteescolar.MainActivity
import com.br.agenteescolar.screens.VisitScreen
import com.br.agenteescolar.viewmodel.VisitViewModel // <-- 2. Importa o SEU ViewModel


fun NavGraphBuilder.visitGraph(navController: NavController) {


    composable(Routes.VISITAS_LIST) {


        val activity = navController.context as MainActivity


        val viewModel: VisitViewModel = viewModel(
            factory = activity.viewModelFactory
        )


        VisitScreen(
            viewModel = viewModel

        )
    }
}
