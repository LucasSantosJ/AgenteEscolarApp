package com.br.agenteescolar.repository

import com.br.agenteescolar.api.AlunoApi
import com.br.agenteescolar.db.AlunoDao
import com.br.agenteescolar.model.Aluno
import kotlinx.coroutines.flow.Flow

class AlunoRepository(
    private val alunoDao: AlunoDao,//BANCO LOCAL
    private val alunoApi: AlunoApi // API EXTERNA
) {
   val alunos: Flow<List<Aluno>> = alunoDao.getAllAlunos()//PARA MANTER A LISTA SEMPRE ATUALIZADA

    suspend fun atualizarAlunosDaApi() {
        val alunosRemotos = alunoApi.findAll()
        alunoDao.deleteAll()
        alunoDao.insertAll(alunosRemotos)
    }

    suspend fun getAlunoPorId(id: Int): Aluno {
        return alunoApi.findById(id)
    }
}