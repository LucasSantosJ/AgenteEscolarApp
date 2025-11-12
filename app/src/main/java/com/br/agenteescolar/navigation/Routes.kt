package com.br.agenteescolar.navigation

object Routes {
    // Rota para a tela principal (Lista de Alunos)
    const val ALUNOS_LIST = "lista_alunos"

    // Rota para a tela de Mapa
    const val MAPA_GERAL = "mapa"

    // Rota para a sua tela de Visitas e Relatórios
    const val VISITAS_LIST = "lista_visitas"

    /**
     * Rota para a tela de Detalhes do Aluno.
     * Note que ela inclui um argumento obrigatório: {alunoId}
     *
     * Para navegar para esta tela, você chamará:
     * navController.navigate("aluno_detalhes/ID_DO_ALUNO_AQUI")
     */
    const val ALUNO_DETALHES = "aluno_detalhes/{alunoId}"

    // Rota "base" para a tela de detalhes (usada para construir a rota no NavHost)
    // Não é estritamente necessário, mas pode ser útil
    const val ALUNO_DETALHES_BASE = "aluno_detalhes"

    // Rota para adicionar um novo aluno
    const val ADD_ALUNO = "add_aluno"


}