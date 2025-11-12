package com.br.agenteescolar.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.br.agenteescolar.model.Aluno
import com.br.agenteescolar.model.Visita

@Database(entities = [Aluno::class, Visita::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // Expõe o DAO para o Repositório
    abstract fun alunoDao(): AlunoDao
    abstract fun visitaDao(): VisitaDao

    companion object {
        // Volatile garante que esta instância seja sempre visível para outros threads
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Retorna a instância se já existir, senão, cria uma nova
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "agente_escolar.db"
                )
                    // : Diz ao Room para recriar o BD se a versão mudar
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}