package com.br.agenteescolar.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.br.agenteescolar.model.Visita
import kotlinx.coroutines.flow.Flow

@Dao
interface VisitaDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(visita: Visita)


    @Query("SELECT * FROM tabela_visita ORDER BY data DESC")
    fun getAllVisitas(): Flow<List<Visita>>

}