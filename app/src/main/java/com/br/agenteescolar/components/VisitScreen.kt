package com.br.agenteescolar.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.agenteescolar.viewmodel.VisitViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisitScreen(
    viewModel: VisitViewModel // 1. Recebemos o NOSSO ViewModel
) {

    // 2. "Escutamos" os StateFlows que vêm do ViewModel
    val todasVisitas by viewModel.todasVisitas.collectAsState()
    val erro by viewModel.erroState.collectAsState()

    // 3. Criamos "variáveis de estado" para os campos de texto do formulário
    // 'remember' diz ao Compose para "lembrar" o que o usuário digitou
    var data by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    var comentario by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("Sao Paulo") } // Deixamos um padrão

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Relatórios de Visita") })
        }
    ) { padding ->
        // 'Column' para empilhar nosso formulário e nossa lista
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp) // Uma borda geral
                .fillMaxSize()
        ) {

            // --- SEÇÃO 1: FORMULÁRIO DE CADASTRO ---

            Text(
                "Registrar Nova Visita",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp)) // Espaçamento

            // Campo de Texto para DATA
            OutlinedTextField(
                value = data,
                onValueChange = { data = it },
                label = { Text("Data da Visita (ex: 12/11/2025)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Campo de Texto para STATUS
            OutlinedTextField(
                value = status,
                onValueChange = { status = it },
                label = { Text("Status (ex: Realizada, Pendente)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Campo de Texto para COMENTÁRIO
            OutlinedTextField(
                value = comentario,
                onValueChange = { comentario = it },
                label = { Text("Comentários da Visita") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Campo de Texto para CIDADE (para a API)
            OutlinedTextField(
                value = cidade,
                onValueChange = { cidade = it },
                label = { Text("Cidade (para buscar o clima)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp)) // Espaço maior

            // Botão de Salvar
            Button(
                onClick = {
                    // 4. "Falamos" com o ViewModel!
                    // Chamamos a função que criamos ontem
                    viewModel.salvarNovaVisita(data, status, comentario, cidade)

                    // Opcional: Limpa os campos após o clique
                    data = ""
                    status = ""
                    comentario = ""
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("SALVAR VISITA")
            }

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
            // Linha divisória
            Spacer(modifier = Modifier.height(16.dp))

            // --- SEÇÃO 2: LISTA DE VISITAS SALVAS ---

            Text(
                "Visitas Registradas",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Lista rolável de visitas
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                // 5. "Lemos" a lista 'todasVisitas' do ViewModel
                items(todasVisitas) { visita ->
                    // Um "Card" simples para mostrar cada visita
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