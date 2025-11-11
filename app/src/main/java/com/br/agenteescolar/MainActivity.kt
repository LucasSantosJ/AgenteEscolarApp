package com.br.agenteescolar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.br.agenteescolar.viewmodel.AppViewModelFactory // Importe
import com.br.agenteescolar.navigation.AppNavHost
import com.br.agenteescolar.ui.theme.AgenteEscolarTheme

class MainActivity : ComponentActivity() {

    // SIM - A factory deve ser uma propriedade da Activity
    lateinit var viewModelFactory: AppViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Crie a Factory aqui. Ela só precisa do contexto.
        // (A Factory agora é responsável por criar o Dao, Api e o Repo)
        viewModelFactory = AppViewModelFactory(applicationContext)

        setContent {
            AgenteEscolarTheme {
                // 2. Chame o AppNavHost. Ele NÃO precisa mais da factory.
                AppNavHost()
            }
        }
    }
}