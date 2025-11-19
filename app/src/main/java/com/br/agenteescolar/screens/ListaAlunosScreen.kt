
import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.icons.filled.LocationOn // Um bom ícone para mapa

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaAlunosScreen(
    viewModel: ListaAlunosViewModel,
    onAlunoClick: (Aluno) -> Unit,
    onAdicionarAlunoClick: () -> Unit
) {
    val context = LocalContext.current
    val alunos by viewModel.alunos.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Lista de Alunos") },
                actions = {
                    IconButton(onClick = { viewModel.atualizar() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Atualizar")
                    }
                    // ... dentro do seu Scaffold ...
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = { Text("Lista de Alunos") },
                            actions = {
                                // O botão de Refresh que JÁ EXISTE:
                                IconButton(onClick = { viewModel.atualizar() }) {
                                    Icon(Icons.Default.Refresh, contentDescription = "Atualizar")
                                }

                                // --- ADICIONE ESTE NOVO BOTÃO AQUI ---
                                IconButton(onClick = {
                                    // 1. Cria a "Intenção" de ir...
                                    // ...do CONTEXTO ATUAL
                                    // ...para a tela do MAPA (MapActivity::class.java)
                                    val intentParaMapa = Intent(context, MapActivity::class.java)

                                    // 2. Executa a "Intenção"
                                    context.startActivity(intentParaMapa)
                                }) {
                                    Icon(
                                        Icons.Default.LocationOn, // O ícone de mapa que importamos
                                        contentDescription = "Abrir Mapa"
                                    )
                                }
                                // --- FIM DO CÓDIGO NOVO ---
                            }
                        )
                    },
// ... o resto do seu código (floatingActionButton, etc) ...
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