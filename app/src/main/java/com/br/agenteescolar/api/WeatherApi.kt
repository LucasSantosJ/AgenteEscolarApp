package com.br.agenteescolar.api

import com.br.agenteescolar.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {


    @GET("{cidade}")
    suspend fun getWeather(
        @Path("cidade", encoded = true) cidade: String,
        @Query("format") format: String = "j1"
    ): WeatherResponse

}