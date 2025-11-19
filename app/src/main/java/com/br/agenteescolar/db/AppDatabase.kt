package com.br.agenteescolar.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.br.agenteescolar.model.Aluno
import com.br.agenteescolar.model.Visita

@Database(entities = [Aluno::class, Visita::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {


    abstract fun alunoDao(): AlunoDao
    abstract fun visitaDao(): VisitaDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "agente_escolar.db"
                )

                    .fallbackToDestructiveMigration(false)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}