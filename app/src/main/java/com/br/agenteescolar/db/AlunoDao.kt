package com.br.agenteescolar.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.br.agenteescolar.model.Aluno
import kotlinx.coroutines.flow.Flow

@Dao
interface AlunoDao {

    //@Query - Busca todos os alunos.

    @Query("SELECT * FROM tabela_aluno ORDER BY nome ASC")
    fun getAllAlunos(): Flow<List<Aluno>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(alunos: List<Aluno>)

    /**
     * Limpa a tabela antes de inserir novos dados da API.
     */
    @Query("DELETE FROM tabela_aluno")
    suspend fun deleteAll()

}