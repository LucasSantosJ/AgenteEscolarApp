package com.br.agenteescolar.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.br.agenteescolar.api.RetrofitAluno
import com.br.agenteescolar.db.AppDatabase
import com.br.agenteescolar.repository.AlunoRepository

/**
 * Factory para criar ViewModels que precisam de dependências
 * (como o AlunoRepository).
 */
class AppViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        // 1. Verifica se a classe pedida é a ListaAlunosViewModel
        if (modelClass.isAssignableFrom(ListaAlunosViewModel::class.java)) {

            // 2. Prepara as dependências que o Repositório precisa

            // Pega o DAO (do banco de dados)
            val dao = AppDatabase.getDatabase(context.applicationContext).alunoDao()

            // Pega a API (do Retrofit)
            val api = RetrofitAluno.api

            // 3. Cria o Repositório com as dependências
            val repository = AlunoRepository(dao, api)

            // 4. Cria e retorna o ViewModel com o Repositório
            @Suppress("UNCHECKED_CAST")
            return ListaAlunosViewModel(repository) as T
        }

        // Se for um ViewModel desconhecido, lança um erro
        throw IllegalArgumentException("Classe ViewModel desconhecida: ${modelClass.name}")
    }
}