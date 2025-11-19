package com.br.agenteescolar.model

import com.google.gson.annotations.SerializedName




data class WeatherResponse(
    @SerializedName("current_condition")
    val currentCondition: List<CurrentCondition>
)


data class CurrentCondition(
    @SerializedName("temp_C")
    val tempC: String, // ex: "25"

    @SerializedName("weatherDesc")
    val weatherDesc: List<WeatherDescription>
)


data class WeatherDescription(
    val value: String
)