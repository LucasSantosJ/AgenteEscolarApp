package com.br.agenteescolar.screens

import AlunoItem
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.br.agenteescolar.model.Aluno
import com.br.agenteescolar.viewmodel.ListaAlunosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaAlunosScreen(
    viewModel: ListaAlunosViewModel,
    onAlunoClick: (Aluno) -> Unit,
    onAdicionarAlunoClick: () -> Unit
) {
    val alunos by viewModel.alunos.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Lista de Alunos") },
                actions = {
                    IconButton(onClick = { viewModel.atualizar() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Atualizar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAdicionarAlunoClick) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Aluno")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (alunos.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(alunos) { aluno ->
                        AlunoItem(aluno = aluno) {
                            onAlunoClick(aluno)
                        }
                    }
                }
            }
        }
    }
}