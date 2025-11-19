package com.br.agenteescolar.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Padrão Singleton (Object) para garantir uma única instância do Retrofit
// para a API de Clima.
object RetrofitWeather {

    private const val BASE_URL = "https://wttr.in/"

    val api: WeatherApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }
}