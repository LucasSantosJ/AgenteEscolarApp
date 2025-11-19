package com.br.agenteescolar.ui.screens.lista_alunos

import AlunoItem
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.br.agenteescolar.model.Aluno
import com.br.agenteescolar.viewmodel.ListaAlunosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaAlunosScreen(
    viewModel: ListaAlunosViewModel,
    onAlunoClick: (Aluno) -> Unit,
    onAdicionarAlunoClick: () -> Unit
) {
    val alunos by viewModel.alunos.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val erro by viewModel.erroState.collectAsStateWithLifecycle()
    val searchText = ""

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Lista de Alunos") },
                actions = {
                    // Mantivemos apenas o botão de Atualizar (Refresh)
                    IconButton(onClick = { viewModel.atualizar() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Atualizar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAdicionarAlunoClick) {
                Icon(Icons.Filled.Add, "Adicionar")
            }
        },
        snackbarHost = {
            if (erro != null) {
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                    action = {
                        TextButton(onClick = { viewModel.limparErro() }) {
                            Text("OK")
                        }
                    }
                ) { Text(text = erro!!) }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Campo de Pesquisa
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { },
                    label = { Text("Pesquisar aluno") },
                    leadingIcon = { Icon(Icons.Filled.Search, "Pesquisar") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    singleLine = true
                )

                // Barra de Progresso
                if (isLoading) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }

                // Lista de Alunos
                if (alunos.isNotEmpty()) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        items(alunos) { aluno ->
                            AlunoItem(aluno = aluno, onClick = { onAlunoClick(aluno) })
                        }
                    }
                }
                else if (!isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Nenhum aluno encontrado.")
                    }
                }
            }

            if (isLoading && alunos.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}