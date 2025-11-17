package com.br.agenteescolar.repository

import android.util.Log
import com.br.agenteescolar.api.WeatherApi
import com.br.agenteescolar.db.VisitaDao
import com.br.agenteescolar.model.Visita
import kotlinx.coroutines.flow.Flow

/**
 * Repositório para gerenciar as operações de Visita.
 * Ele recebe o VisitaDao e o WeatherApi como "ferramentas"
 * para poder trabalhar.
 */
class VisitaRepository(
    private val visitaDao: VisitaDao, // O DAO que criamos
    private val weatherApi: WeatherApi // A API de clima que criamos
) {

    /**
     * Expõe um Flow (fluxo) de todas as visitas salvas no banco.
     * O ViewModel vai "ouvir" este fluxo para mostrar a lista na tela.
     */
    val todasVisitas: Flow<List<Visita>> = visitaDao.getAllVisitas()

    /**
     * Esta é a função principal da nossa funcionalidade!
     * 1. Recebe a Visita (com data, status, comentario)
     * 2. Recebe a Cidade para buscar o clima
     * 3. CHAMA A API de Clima (usando Coroutine)
     * 4. Pega o resultado da API e salva no objeto Visita
     * 5. SALVA a visita completa no Banco de Dados (usando Coroutine)
     */
    suspend fun salvarNovaVisita(visita: Visita, cidade: String) {
        try {
            // 1. CHAMA A API (I/O - Operação de Rede)
            // A palavra 'suspend' faz a Coroutine "pausar" aqui
            // até a API responder, sem travar a tela.
            val weatherResponse = weatherApi.getWeather(cidade = cidade)

            // 2. Processa a resposta da API
            // Pegamos a primeira descrição do clima e a temperatura
            val climaDesc = weatherResponse.currentCondition.firstOrNull()?.weatherDesc?.firstOrNull()?.value ?: "N/A"
            val temp = weatherResponse.currentCondition.firstOrNull()?.tempC ?: "0"
            val climaFormatado = "$climaDesc, ${temp}°C"

            // 3. Atualiza o objeto Visita com os dados do clima
            // .copy() cria uma cópia da visita, mas com o campo 'clima' atualizado
            val visitaComClima = visita.copy(clima = climaFormatado)

            // 4. SALVA NO BANCO (I/O - Operação de Disco)
            // A Coroutine "pausa" aqui até o banco terminar de salvar.
            visitaDao.insert(visitaComClima)

        } catch (e: Exception) {
            // 5. Tratamento de Erro
            // Se a API falhar (ex: sem internet),
            // salvamos a visita assim mesmo, mas com o clima "Erro".
            Log.e("VisitaRepository", "Falha ao buscar clima: ${e.message}")
            val visitaSemClima = visita.copy(clima = "Erro ao buscar clima")
            visitaDao.insert(visitaSemClima)
        }
    }
}