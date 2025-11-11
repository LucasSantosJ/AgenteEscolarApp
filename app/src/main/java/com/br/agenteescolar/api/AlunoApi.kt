package com.br.agenteescolar.api

import com.br.agenteescolar.model.Aluno
import retrofit2.http.GET
import retrofit2.http.Path

interface AlunoApi {
    @GET("alunos")
    suspend fun findAll(): List<Aluno>

    @GET("alunos/{id}")
    suspend fun findById(@Path("id") id: Int): Aluno
}