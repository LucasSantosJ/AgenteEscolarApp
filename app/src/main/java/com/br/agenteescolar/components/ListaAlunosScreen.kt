package com.br.agenteescolar.components

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
    val isLoading by viewModel.isLoading.collectAsState()
    val erro by viewModel.erroState.collectAsState()

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

            // 🔵 MOSTRAR LOADING APENAS QUANDO isLoading = true
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                return@Box
            }

            // 🔴 MOSTRAR MENSAGEM DE ERRO
            if (erro != null) {
                Text(
                    text = erro ?: "",
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.error
                )
                return@Box
            }

            // 🟡 LISTA VAZIA (MAS NÃO ESTAMOS MAIS CARREGANDO)
            if (alunos.isEmpty()) {
                Text(
                    text = "Nenhum aluno encontrado.",
                    modifier = Modifier.align(Alignment.Center)
                )
                return@Box
            }

            // 🟢 LISTA NORMAL
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
