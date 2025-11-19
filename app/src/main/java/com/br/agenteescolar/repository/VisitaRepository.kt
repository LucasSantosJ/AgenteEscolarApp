package com.br.agenteescolar.repository

import android.util.Log
import com.br.agenteescolar.api.WeatherApi
import com.br.agenteescolar.db.VisitaDao
import com.br.agenteescolar.model.Visita
import kotlinx.coroutines.flow.Flow


class VisitaRepository(
    private val visitaDao: VisitaDao,
    private val weatherApi: WeatherApi
) {

    val todasVisitas: Flow<List<Visita>> = visitaDao.getAllVisitas()

    suspend fun salvarNovaVisita(visita: Visita, cidade: String) {
        try {

            val weatherResponse = weatherApi.getWeather(cidade = cidade)


            val climaDesc = weatherResponse.currentCondition.firstOrNull()?.weatherDesc?.firstOrNull()?.value ?: "N/A"
            val temp = weatherResponse.currentCondition.firstOrNull()?.tempC ?: "0"
            val climaFormatado = "$climaDesc, ${temp}°C"


            val visitaComClima = visita.copy(clima = climaFormatado)


            visitaDao.insert(visitaComClima)

        } catch (e: Exception) {

            Log.e("VisitaRepository", "Falha ao buscar clima: ${e.message}")
            val visitaSemClima = visita.copy(clima = "Erro ao buscar clima")
            visitaDao.insert(visitaSemClima)
        }
    }
}