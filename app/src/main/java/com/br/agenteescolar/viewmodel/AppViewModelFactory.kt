package com.br.agenteescolar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.br.agenteescolar.repository.AlunoRepository

/**
 * Factory responsável por criar instâncias de ViewModels
 * que precisam de dependências (como o Repository).
 */
class AppViewModelFactory(
    private val repository: AlunoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListaAlunosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListaAlunosViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel desconhecido: ${modelClass.name}")
    }
}
