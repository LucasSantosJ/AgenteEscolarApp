package com.br.agenteescolar.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.br.agenteescolar.api.RetrofitAluno
import com.br.agenteescolar.api.RetrofitWeather
import com.br.agenteescolar.db.AppDatabase
import com.br.agenteescolar.repository.AlunoRepository
import com.br.agenteescolar.repository.VisitaRepository

/**
 * Factory para criar ViewModels que precisam de dependências
 * (como o AlunoRepository).
 */
class AppViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val database = AppDatabase.getDatabase(context.applicationContext)


        // 1. Verifica se a classe pedida é a ListaAlunosViewModel
        if (modelClass.isAssignableFrom(ListaAlunosViewModel::class.java)) {

            // 2. Prepara as dependências que o Repositório precisa

            // Pega o DAO (do banco de dados)
            val dao = database.alunoDao()

            // Pega a API (do Retrofit)
            val api = RetrofitAluno.api

            // 3. Cria o Repositório com as dependências
            val repository = AlunoRepository(dao, api)

            // 4. Cria e retorna o ViewModel com o Repositório
            @Suppress("UNCHECKED_CAST")
            return ListaAlunosViewModel(repository) as T
        }

        if (modelClass.isAssignableFrom(VisitViewModel::class.java)) {

            // Prepara as dependências do VisitaRepository
            val dao = database.visitaDao() // <-- MUDANÇA 3: Usamos o visitaDao()
            val api = RetrofitWeather.api // <-- MUDANÇA 4: Usamos o RetrofitWeather.api
            val repository = VisitaRepository(dao, api) // <-- MUDANÇA 5: Criamos o VisitaRepository

            // 4. Cria e retorna o VisitViewModel com o Repositório
            @Suppress("UNCHECKED_CAST")
            return VisitViewModel(repository) as T // <-- MUDANÇA 6: Retornamos o VisitViewModel
        }



        // Se for um ViewModel desconhecido, lança um erro
        throw IllegalArgumentException("Classe ViewModel desconhecida: ${modelClass.name}")
    }
}