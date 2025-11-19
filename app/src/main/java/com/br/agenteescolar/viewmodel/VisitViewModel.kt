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


class VisitViewModel(
    private val repository: VisitaRepository
) : ViewModel() {

    val todasVisitas = repository.todasVisitas
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(0), emptyList())


    private val _erroState = MutableStateFlow<String?>(null)
    val erroState = _erroState.asStateFlow()

    fun salvarNovaVisita(data: String, status: String, comentario: String, cidade: String) {

        viewModelScope.launch {
            try {

                val novaVisita = Visita(
                    data = data,
                    status = status,
                    comentario = comentario

                )


                repository.salvarNovaVisita(novaVisita, cidade)


                _erroState.value = null

            } catch (e: Exception) {

                _erroState.value = "Falha ao salvar a visita. Tente novamente."
                e.printStackTrace()
            }
        }
    }

    fun limparErro() {
        _erroState.value = null
    }
}