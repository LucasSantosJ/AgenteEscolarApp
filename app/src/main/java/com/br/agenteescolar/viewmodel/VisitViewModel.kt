package com.br.agenteescolar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.agenteescolar.model.Visita
import com.br.agenteescolar.repository.VisitaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel para as telas de Visita (Cadastro e Lista).
 * Recebe o VisitaRepository via injeção de dependência.
 */
class VisitViewModel(
    private val repository: VisitaRepository
) : ViewModel() {

    val todasVisitas = repository.todasVisitas
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(0), emptyList())


    private val _erroState = MutableStateFlow<String?>(null)
    val erroState = _erroState.asStateFlow()

    fun salvarNovaVisita(data: String, status: String, comentario: String, cidade: String) {

        // 5. Inicia a Coroutine no escopo do ViewModel
        viewModelScope.launch {
            try {
                // 6. Cria o objeto Visita com os dados da tela
                val novaVisita = Visita(
                    data = data,
                    status = status,
                    comentario = comentario

                )

                // 7. Chama a função SUSPENSA do repositório
                // É aqui que a API e o Banco são chamados!
                repository.salvarNovaVisita(novaVisita, cidade)

                // Se deu certo, limpa qualquer erro antigo
                _erroState.value = null

            } catch (e: Exception) {
                // 8. Se falhar, avisa a UI pelo StateFlow de erro
                _erroState.value = "Falha ao salvar a visita. Tente novamente."
                e.printStackTrace()
            }
        }
    }

    fun limparErro() {
        _erroState.value = null
    }
}