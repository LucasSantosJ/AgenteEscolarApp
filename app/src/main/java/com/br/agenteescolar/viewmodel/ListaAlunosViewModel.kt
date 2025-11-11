package com.br.agenteescolar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.agenteescolar.repository.AlunoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListaAlunosViewModel(
    private val repository: AlunoRepository
) : ViewModel() {

    val alunos = repository.alunos
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun atualizar() {
        viewModelScope.launch {
            repository.atualizarAlunosDaApi()
        }
    }
}
