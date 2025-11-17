package com.br.agenteescolar.model

import androidx.room.Entity
import androidx.room.PrimaryKey

// Define a tabela 'tabela_visita' no banco de dados
@Entity(tableName = "tabela_visita")
data class Visita(

    // Chave primária com auto-generate.
    // Diferente do Aluno, a Visita é criada DENTRO do app,
    // então deixamos o Room cuidar do ID para nós.
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // Chave estrangeira para ligar a visita ao aluno
    // val alunoId: Int,  // Vamos adicionar isso depois para não complicar

    val data: String = "",
    val status: String = "", // Ex: "Realizada", "Pendente", "Cancelada"
    val comentario: String = "",
    val clima: String = "" // Onde vamos salvar o dado da API de Clima
)