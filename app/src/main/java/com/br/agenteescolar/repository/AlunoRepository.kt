package com.br.agenteescolar.repository

import com.br.agenteescolar.api.RetrofitAluno
import com.br.agenteescolar.db.AlunoDao
import com.br.agenteescolar.model.Aluno
import kotlinx.coroutines.flow.Flow

class AlunoRepository(
    private val alunoDao: AlunoDao
) {
    val alunos: Flow<List<Aluno>> = alunoDao.getAllAlunos()

    suspend fun atualizarAlunosDaApi() {
        try {
            val alunosRemotos = RetrofitAluno.propertyApi.findAll()
            alunoDao.deleteAll()
            alunoDao.insertAll(alunosRemotos)
        } catch (e: Exception) {
            e.printStackTrace() // opcional: tratar erro de rede
        }
    }

    suspend fun getAlunoPorId(id: Int): Aluno? {
        return try {
            RetrofitAluno.propertyApi.findById(id)
        } catch (e: Exception) {
            null
        }
    }
}