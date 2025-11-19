package com.br.agenteescolar.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.agenteescolar.viewmodel.VisitViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisitScreen(
    viewModel: VisitViewModel
) {


    val todasVisitas by viewModel.todasVisitas.collectAsState()
    val erro by viewModel.erroState.collectAsState()


    var data by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    var comentario by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("Sao Paulo") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Relatórios de Visita") })
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {



            Text(
                "Registrar Nova Visita",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))


            OutlinedTextField(
                value = data,
                onValueChange = { data = it },
                label = { Text("Data da Visita (ex: 12/11/2025)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))


            OutlinedTextField(
                value = status,
                onValueChange = { status = it },
                label = { Text("Status (ex: Realizada, Pendente)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))


            OutlinedTextField(
                value = comentario,
                onValueChange = { comentario = it },
                label = { Text("Comentários da Visita") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))


            OutlinedTextField(
                value = cidade,
                onValueChange = { cidade = it },
                label = { Text("Cidade (para buscar o clima)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))


            Button(
                onClick = {

                    viewModel.salvarNovaVisita(data, status, comentario, cidade)


                    data = ""
                    status = ""
                    comentario = ""
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("SALVAR VISITA")
            }

            Spacer(modifier = Modifier.height(24.dp))
            Divider() // Linha divisória
            Spacer(modifier = Modifier.height(16.dp))



            Text(
                "Visitas Registradas",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))


            LazyColumn(modifier = Modifier.fillMaxWidth()) {

                items(todasVisitas) { visita ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                "Data: ${visita.data} - Status: ${visita.status}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Clima: ${visita.clima}")
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Observação: ${visita.comentario}")
                        }
                    }
                }
            }
        }
    }
}