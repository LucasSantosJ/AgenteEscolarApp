package com.br.agenteescolar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.agenteescolar.repository.AlunoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
//Conecta o repository
class ListaAlunosViewModel(
    private val repository: AlunoRepository
) : ViewModel() {

    val alunos = repository.alunos
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // StateFlow para expor o erro para a UI
    private val _erroState = MutableStateFlow<String?>(null)
    val erroState = _erroState.asStateFlow()

    fun atualizar() {
        viewModelScope.launch {
            try {
                repository.atualizarAlunosDaApi()
                _erroState.value = null // Limpa o erro se der certo

            } catch (e: Exception) {
                //  Captua o erro e avise a UI
                _erroState.value = "Falha ao atualizar. Verifique sua conexão."
                e.printStackTrace()
            }
        }
    }

    /**
     * Função para a UI chamar depois de mostrar o erro,
     * para "limpar" a mensagem.
     */
    fun limparErro() {
        _erroState.value = null
    }
}