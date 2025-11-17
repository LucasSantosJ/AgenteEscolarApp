package com.br.agenteescolar.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Padrão Singleton (Object) para garantir uma única instância do Retrofit
// para a API de Clima.
object RetrofitWeather {

    // 1. A URL base da nossa API de Clima
    private const val BASE_URL = "https://wttr.in/"

    // 2. O construtor do Retrofit (preguiçoso/lazy)
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // O "tradutor" GSON
            .build()
    }

    // 3. A "entrega" da nossa interface pronta para uso
    val api: WeatherApi by lazy {
        retrofit.create(WeatherApi::class.java)
    }
}