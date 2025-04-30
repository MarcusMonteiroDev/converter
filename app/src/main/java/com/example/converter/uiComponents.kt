package com.example.converter

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

// Componentes de UI utilizados no aplicativo:
// Menu expandido (DropDownMenu)
@Composable
fun ModeloMenu (modifier: Modifier = Modifier,
                isDropDownExpanded: MutableState<Boolean>,
                itemPosition: MutableState<Int>,
                opcoes: List<String>) {
    Box (modifier = modifier) {
        Box(
            modifier = Modifier                                 // Aplica os modificadores ao box
                .border(                                            // Adiciona uma borda ao box
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(4.dp)
                )
                // Ao clicar, inverte o estado do DropdownMenu (abre ou fecha)
                .clickable { isDropDownExpanded.value = true }        // Torna a box clicável
                .padding(horizontal = 12.dp, vertical = 8.dp)      // Espaçamento interno
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),                 // Preenche a linha inteira
                verticalAlignment = Alignment.CenterVertically,     // Alinha verticalmente ao centro
                horizontalArrangement = Arrangement.SpaceBetween    // Distribui igualmente os itens
            ) {
                // Exibe o texto da opção selecionada com base no índice armazenado em itemPosition
                Text(
                    modifier = Modifier.weight(1f),
                    text = opcoes[itemPosition.value]
                )

                // Ícone da seta para baixo
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Abrir menu"
                )
            }
        }

        // DropdownMenu para exibir a lista de opções
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(), // Preenche a linha inteira
            expanded = isDropDownExpanded.value, // Controla a visibilidade do menu
            onDismissRequest = {
                // Fecha o menu quando o usuário clica fora dele
                isDropDownExpanded.value = false
            }) {
            // Itera sobre a lista de opções e seus índices
            opcoes.forEachIndexed { index, item ->
                // Cria um DropdownMenuItem para cada opção
                DropdownMenuItem(
                    text = {
                        // Exibe o texto da opção no item de menu
                        Text(text = item)
                    },
                    onClick = {
                        // Ao clicar em um item de menu:
                        // Fecha o DropdownMenu
                        isDropDownExpanded.value = false
                        // Atualiza o índice da opção selecionada
                        itemPosition.value = index
                    }
                )
            }
        }
    }
}

// Tela de conversão customizável
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitScreen(
    modifier: Modifier = Modifier,              // Modificador para customização da tela
    onGoBack: () -> Unit,                       // Função de retorno à tela anterior
    unidades: List<String>,                     // Lista de unidades de medida disponíveis
    titulo: String,                             // Título da tela
    tipo_conversao: Int
) {
    // Registra o input do usuário
    var inputUnidade by remember { mutableStateOf("") }
    // Armazenam a posição do menu expandido para saber quais unidades estão sendo convertidas
    val itemPosition1 = remember { mutableStateOf(0) }
    val itemPosition2 = remember { mutableStateOf(1) }

    // Tela principal da activity
    // Define o scaffold com o cabeçalho e o conteúdo
    Scaffold (
        // Cabeçalho da tela (barra no topo)
        topBar = {
            TopAppBar (title = { Text(text = "Converter $titulo") },
                navigationIcon = { IconButton(onClick = {onGoBack()}) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar") }
                }
            )
        }
    ) {innerPadding ->  // Espaçamento interno para o conteúdo (promovido pelo scaffold)
        // Linha divisória entre o cabeçalho e o conteúdo
        HorizontalDivider(
            modifier = Modifier.padding(innerPadding),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline,
        )
        // Coluna principal da tela
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp),
            horizontalAlignment = Alignment.Start)
        {
            // Texto da tela
            Text (text = "Converter de",
                style = MaterialTheme.typography.titleMedium)
            // Espaçamento
            Spacer (modifier = Modifier.height(8.dp))
            // Menu expandido para selecionar a unidade de entrada
            ModeloMenu (
                modifier = Modifier.fillMaxWidth(),
                isDropDownExpanded = remember { mutableStateOf(false) },
                itemPosition = itemPosition1,
                opcoes = unidades
            )
            // Espaçamento
            Spacer (modifier = Modifier.height(8.dp))
            // Campo de texto para inserir o valor a ser convertido
            TextField (modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedLabelColor = MaterialTheme.colorScheme.primary),
                value = inputUnidade,
                onValueChange = {inputUnidade = it},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text(text = "Valor")},
                singleLine = true)
            // Espaçamento
            Spacer (modifier = Modifier.height(8.dp))
            // Texto da tela
            Text (text = "Para:",
                style = MaterialTheme.typography.titleMedium)
            // Espaçamento
            Spacer (modifier = Modifier.height(8.dp))
            // Menu expandido para selecionar a unidade de saída
            ModeloMenu (
                modifier = Modifier.fillMaxWidth(),
                isDropDownExpanded = remember { mutableStateOf(false) },
                itemPosition = itemPosition2,
                opcoes = unidades
            )
            // Espaçamento
            Spacer(modifier = Modifier.height(24.dp)) // Espaço antes da seção de resultado
            // Caixa de resultado
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                tonalElevation = 2.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Resultado:",
                        style = MaterialTheme.typography.titleMedium
                    )
                    // Espaçamento
                    Spacer(modifier = Modifier.height(8.dp)) // Espaço entre o rótulo e o valor
                    // Resultado da conversão
                    when (tipo_conversao) {
                        // Conversão de área
                        1 -> {Text(
                            text = convertArea(
                                inputUnidade = inputUnidade,
                                itemPosition1 = itemPosition1.value,
                                itemPosition2 = itemPosition2.value),
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.primary
                        )}
                        // Conversão de comprimento
                        2 -> {Text(
                            text = convertComprimento(
                                inputUnidade = inputUnidade,
                                itemPosition1 = itemPosition1.value,
                                itemPosition2 = itemPosition2.value),
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.primary
                        )}
                        // Conversão de dados
                        3 -> {Text(
                            text = convertDados(
                                inputUnidade = inputUnidade,
                                itemPosition1 = itemPosition1.value,
                                itemPosition2 = itemPosition2.value),
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.primary
                        )}
                        // Conversão de massa
                        4 -> {Text(
                            text = convertMassa(
                                inputUnidade = inputUnidade,
                                itemPosition1 = itemPosition1.value,
                                itemPosition2 = itemPosition2.value),
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.primary
                        )}
                        // Conversão de pressão
                        5 -> {Text(
                            text = convertPressao(
                                inputUnidade = inputUnidade,
                                itemPosition1 = itemPosition1.value,
                                itemPosition2 = itemPosition2.value),
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.primary
                        )}
                        // Conversão de temperatura
                        6 -> {Text(
                            text = convertTemperatura(
                                inputUnidade = inputUnidade,
                                itemPosition1 = itemPosition1.value,
                                itemPosition2 = itemPosition2.value),
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.primary
                        )}
                        // Conversão de tempo
                        7 -> {Text(
                            text = convertTempo(
                                inputUnidade = inputUnidade,
                                itemPosition1 = itemPosition1.value,
                                itemPosition2 = itemPosition2.value),
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.primary
                        )}
                        // Conversão de velocidade
                        8 -> {Text(
                            text = convertVelocidade(
                                inputUnidade = inputUnidade,
                                itemPosition1 = itemPosition1.value,
                                itemPosition2 = itemPosition2.value),
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.primary
                        )}
                        // Conversão de volume
                        9 -> {Text(
                            text = convertVolume(
                                inputUnidade = inputUnidade,
                                itemPosition1 = itemPosition1.value,
                                itemPosition2 = itemPosition2.value),
                            style = MaterialTheme.typography.displaySmall,
                            color = MaterialTheme.colorScheme.primary
                        )}
                    }
                }
            }
        }
    }
}