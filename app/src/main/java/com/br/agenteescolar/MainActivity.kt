package com.br.agenteescolar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.br.agenteescolar.db.AppDatabase
import com.br.agenteescolar.repository.AlunoRepository
import com.br.agenteescolar.viewmodel.AppViewModelFactory
import com.br.agenteescolar.navigation.AppNavHost
import com.br.agenteescolar.ui.theme.AgenteEscolarTheme

class MainActivity : ComponentActivity() {

    // ✅ Repositório e ViewModelFactory globais
    lateinit var viewModelFactory: AppViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Cria banco e repositório
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = AlunoRepository(database.alunoDao())

        // Instancia a Factory
        viewModelFactory = AppViewModelFactory(repository)

        setContent {
            AgenteEscolarTheme {
                AppNavHost()
            }
        }
    }
}
