package com.br.agenteescolar.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.br.agenteescolar.model.Visita
import kotlinx.coroutines.flow.Flow

@Dao
interface VisitaDao {

    /**
     * Insere uma nova visita no banco.
     * Se a visita já existir (pelo ID), ela será substituída.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(visita: Visita)

    /**
     * Busca todas as visitas cadastradas, ordenadas pela data mais recente.
     * Retorna um Flow, que atualiza a lista automaticamente
     * sempre que uma nova visita for inserida.
     */
    @Query("SELECT * FROM tabela_visita ORDER BY data DESC")
    fun getAllVisitas(): Flow<List<Visita>>

}