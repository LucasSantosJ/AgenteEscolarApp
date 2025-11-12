package com.br.agenteescolar.model

import com.google.gson.annotations.SerializedName

/**
 * Este arquivo define os "moldes" (Data Classes) para que o GSON
 * (a biblioteca que o Retrofit usa) saiba como "traduzir"
 * o JSON da API de clima para objetos Kotlin.
 */

// Classe principal que 'envelopa' a resposta da API
data class WeatherResponse(
    // @SerializedName é usado se o nome no JSON for
    // diferente do nome da nossa variável. (JSON usa 'current_condition')
    @SerializedName("current_condition")
    val currentCondition: List<CurrentCondition>
)

// Representa o item dentro da lista 'current_condition'
data class CurrentCondition(
    @SerializedName("temp_C")
    val tempC: String, // ex: "25"

    @SerializedName("weatherDesc")
    val weatherDesc: List<WeatherDescription>
)

// Representa o item dentro da lista 'weatherDesc'
data class WeatherDescription(
    val value: String // ex: "Ensolarado", "Nublado"
)