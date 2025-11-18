package com.br.agenteescolar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.br.agenteescolar.viewmodel.AppViewModelFactory // Importe
import com.br.agenteescolar.navigation.AppNavHost
import com.br.agenteescolar.ui.theme.AgenteEscolarTheme

class MainActivity : ComponentActivity() {

    lateinit var viewModelFactory: AppViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModelFactory = AppViewModelFactory(applicationContext)

        setContent {
            AgenteEscolarTheme {
                AppNavHost()
            }
        }
    }
}