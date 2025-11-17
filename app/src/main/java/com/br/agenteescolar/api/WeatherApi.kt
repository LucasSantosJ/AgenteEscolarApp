package com.br.agenteescolar.api

import com.br.agenteescolar.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {

    /**
     * Busca o clima para uma cidade específica.
     * Exemplo da URL que será montada:
     * https://wttr.in/Sao+Paulo?format=j1
     */
    @GET("{cidade}")
    suspend fun getWeather(
        @Path("cidade") cidade: String,
        @Query("format") format: String = "j1"
    ): WeatherResponse

}