package com.br.agenteescolar.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.br.agenteescolar.api.RetrofitAluno
import com.br.agenteescolar.api.RetrofitWeather
import com.br.agenteescolar.db.AppDatabase
import com.br.agenteescolar.repository.AlunoRepository
import com.br.agenteescolar.repository.VisitaRepository

class AppViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val db = AppDatabase.getDatabase(context)

        if (modelClass.isAssignableFrom(ListaAlunosViewModel::class.java)) {

            val alunoDao = db.alunoDao()
            val alunoApi = RetrofitAluno.api
            val alunoRepository = AlunoRepository(alunoDao, alunoApi)

            @Suppress("UNCHECKED_CAST")
            return ListaAlunosViewModel(alunoRepository) as T
        }

        if (modelClass.isAssignableFrom(VisitViewModel::class.java)) {

            val visitaDao = db.visitaDao()
            val weatherApi = RetrofitWeather.api
            val visitaRepository = VisitaRepository(visitaDao, weatherApi)

            @Suppress("UNCHECKED_CAST")
            return VisitViewModel(visitaRepository) as T
        }

        throw IllegalArgumentException("Classe ViewModel desconhecida: ${modelClass.name}")
    }
}
