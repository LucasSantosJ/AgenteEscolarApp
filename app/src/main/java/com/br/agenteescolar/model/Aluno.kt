package com.br.agenteescolar.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabela_aluno")
data class Aluno(

    @PrimaryKey(autoGenerate = false) // Usaremos o ID da API/JSON como chave primária
    val id: Int = 0,
    val nome: String = "",
    val escola: String = "",
    val status: String = "", // Ex: "Ativo", "Aguardando Visita"
    val cep: String = "",
    val nomeResponsavel: String = ""

)