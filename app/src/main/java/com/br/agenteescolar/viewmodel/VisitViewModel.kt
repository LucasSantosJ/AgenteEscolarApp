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
    private val repository: VisitaRepository // <-- 1. Recebe o NOSSO repositório
) : ViewModel() {

    /**
     * 2. Expõe a lista de visitas (o Flow do repositório)
     * como um StateFlow para a UI "assinar".
     */
    val todasVisitas = repository.todasVisitas
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /**
     * 3. StateFlow para expor erros para a UI
     * (Exatamente como no AlunoViewModel).
     */
    private val _erroState = MutableStateFlow<String?>(null)
    val erroState = _erroState.asStateFlow()

    /**
     * 4. Ação principal: Salva uma nova visita.
     * A tela (UI) vai chamar esta função quando o usuário
     * clicar no botão "Salvar".
     */
    fun salvarNovaVisita(data: String, status: String, comentario: String, cidade: String) {

        // 5. Inicia a Coroutine no escopo do ViewModel
        viewModelScope.launch {
            try {
                // 6. Cria o objeto Visita com os dados da tela
                val novaVisita = Visita(
                    data = data,
                    status = status,
                    comentario = comentario
                    // O 'id' é 0 (autoGenerate)
                    // O 'clima' será preenchido lá no repositório
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

    /**
     * 9. Função para a UI "limpar" a mensagem de erro.
     */
    fun limparErro() {
        _erroState.value = null
    }
}