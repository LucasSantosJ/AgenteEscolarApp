package com.br.agenteescolar.ui.screens.lista_alunos

import AlunoItem
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
    //  Coletamos os estados individualmente
    // O Room garante que 'alunos' sempre tenha a versão mais recente do cache
    val alunos by viewModel.alunos.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val erro by viewModel.erroState.collectAsStateWithLifecycle()

    // Por enquanto, deixei um estado local ou fixo se não houver no VM.
    val searchText = ""

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Lista de Alunos") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAdicionarAlunoClick) {
                Icon(Icons.Filled.Add, "Adicionar")
            }
        },
        // O SnackbarHost exibe o erro sem tampar a tela inteira
        snackbarHost = {
            if (erro != null) {
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                    action = {
                        androidx.compose.material3.TextButton(onClick = { viewModel.limparErro() }) {
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

                // Campo de Pesquisa (Fixo no topo)
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { /* Implementar lógica de busca no VM */ },
                    label = { Text("Pesquisar aluno") },
                    leadingIcon = { Icon(Icons.Filled.Search, "Pesquisar") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    singleLine = true
                )

                // 2. Barra de Progresso Não-Bloqueante
                // Se a lista já existe mas estamos atualizando, mostramos essa barra fina.
                if (isLoading) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }

                // 3. Lista de Alunos (Prioridade Máxima)
                // Se o banco tem dados, mostramos eles IMEDIATAMENTE.
                if (alunos.isNotEmpty()) {
                    LazyColumn(
                        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        items(alunos) { aluno ->
                            AlunoItem(aluno = aluno, onClick = { onAlunoClick(aluno) })
                        }
                    }
                }
                // Se não tem dados e parou de carregar, aí sim a lista está vazia.
                else if (!isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Nenhum aluno encontrado.")
                    }
                }
            }

            // Loading Inicial (Bloqueante)
            // Só mostramos o círculo grande no meio se NÃO houver dados para mostrar.
            // Isso evita a "tela branca" inicial.
            if (isLoading && alunos.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}