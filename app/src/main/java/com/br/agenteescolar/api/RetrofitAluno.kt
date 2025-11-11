package com.br.agenteescolar.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitAluno {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://my-json-server.typicode.com/LucasSantosJ/AgenteEscolarAPI/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: AlunoApi = retrofit.create(AlunoApi::class.java)
}