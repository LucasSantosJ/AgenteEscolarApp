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
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(0), emptyList())

    private val _erroState = MutableStateFlow<String?>(null)
    val erroState = _erroState.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        //  Atualiza automaticamente na primeira execução
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.atualizarAlunosDaApi()
                _erroState.value = null
            } catch (e: Exception) {
                _erroState.value = "Falha ao carregar dados. Verifique sua conexão."
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun atualizar() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.atualizarAlunosDaApi()
                _erroState.value = null
            } catch (e: Exception) {
                _erroState.value = "Falha ao atualizar. Verifique sua conexão."
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun limparErro() {
        _erroState.value = null
    }
}
