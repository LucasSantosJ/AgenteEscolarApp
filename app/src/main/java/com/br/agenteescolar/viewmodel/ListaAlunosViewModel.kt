package com.br.agenteescolar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.agenteescolar.repository.AlunoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListaAlunosViewModel(
    private val repository: AlunoRepository
) : ViewModel() {

    // 1. Estado do texto da pesquisa
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    // 2. A lista agora é uma COMBINAÇÃO: (Banco de Dados + Texto da Pesquisa)
    // Sempre que um dos dois mudar, essa lista se atualiza automaticamente.
    val alunos = combine(repository.alunos, _searchText) { listaAlunos, texto ->
        if (texto.isBlank()) {
            listaAlunos // Se não tem busca, retorna tudo
        } else {
            // Se tem busca, filtra pelo nome ou escola
            listaAlunos.filter { aluno ->
                aluno.nome.contains(texto, ignoreCase = true) ||
                        aluno.escola.contains(texto, ignoreCase = true)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _erroState = MutableStateFlow<String?>(null)
    val erroState = _erroState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        atualizar()
    }

    fun atualizar() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _erroState.value = null
                repository.atualizarAlunosDaApi()
            } catch (e: Exception) {
                _erroState.value = "Falha ao atualizar. Verifique sua conexão."
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // 3. Função para a tela chamar quando o usuário digita
    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun limparErro() {
        _erroState.value = null
    }
}