package com.br.agenteescolar.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tabela_visita")
data class Visita(


    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val data: String = "",
    val status: String = "",
    val comentario: String = "",
    val clima: String = ""
)