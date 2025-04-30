package com.example.converter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.converter.ui.theme.ConverterTheme
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.material3.Surface
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.TextField
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.text.style.LineHeightStyle

//--------------------------------------------------------------------------------------------------
// Lista das telas do aplicativo
// A tela Home é a tela inicial do aplicativo e as demais são as telas das unidades de conversão
enum class Screen {
    Home,               // Tela inicial
    Area,
    Comprimento,
    Dados,
    Massa,
    Pressao,
    Temperatura,
    Tempo,
    Velocidade,
    Volume
}
//--------------------------------------------------------------------------------------------------
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
//--------------------------------------------------------------------------------------------------
// Funções de conversão:
// Conversão Área
fun convertArea (inputUnidade: String,      // Input do usuário
                 itemPosition1: Int,        // Unidade de entrada (de)
                 itemPosition2: Int         // Unidade de saída (para)
): String {
    /*
    Lista de unidades disponíveis
    [0] -> Quilômetro quadrado (km²)
    [1] -> Hectare (ha)
    [2] -> Are (a)
    [3] -> Metro quadrado (m²)
    [4] -> Decímetro quadrado (dm²)
    [5] -> Centímetro quadrado (cm²)
    [6] -> Milímetro quadrado (mm²)
    [7] -> Micrômetro quadrado (µm²)
    [8] -> Nanômetro quadrado (nm²)
    */

    // Resultado da conversão
    var resultado: String = "0"
    // Variável usada no cpalculo. Tenta converter o input do usuário para um número
    // caso não consiga, retorna 0
    var variavel: Double = inputUnidade.toDoubleOrNull() ?: 0.0

    // Lista de unidades de medida em m²
    val unidadesEmM2 = listOf(
        1_000_000.0, // km²
        10_000.0,    // ha
        100.0,       // a
        1.0,         // m²
        0.01,        // dm²
        0.0001,      // cm²
        0.000001,    // mm²
        1.0E-12,     // µm²
        1.0E-18      // nm²
    )

    // Primeiro o input do usuário é convertido para m²
    // Depois o resultado é convertido para a unidade de saída
    // Essa abordagem evita o uso de condicionais para a conversão
    val valorEmM2 = variavel * unidadesEmM2[itemPosition1]
    resultado = (valorEmM2 / unidadesEmM2[itemPosition2]).toString()

    // Retorna o resultado formatado
    return resultado
}

// Conversão comprimento
fun convertComprimento(inputUnidade: String,      // Input do usuário
                       itemPosition1: Int,        // Unidade de entrada (de)
                       itemPosition2: Int         // Unidade de saída (para)
): String {
    /*
    Lista de unidades disponíveis
    [0] -> Quilômetro (km)
    [1] -> Metro (m)
    [2] -> Decímetro (dm)
    [3] -> Centímetro (cm)
    [4] -> Milímetro (mm)
    [5] -> Micrômetro (µm)
    [6] -> Nanômetro (nm)
    [7] -> Angstrom (Å)
    [8] -> Liga
    [9] -> Milha (mi)
    [10] -> Furlong
    [11] -> Corrente
    [12] -> Barra (rd)
    [13] -> Jarda (yd)
     */

    // Resultado da conversão
    var resultado: String = "0"
    // Variável usada no cpalculo. Tenta converter o input do usuário para um número
    // caso não consiga, retorna 0
    var variavel: Double = inputUnidade.toDoubleOrNull() ?: 0.0

    // Lista de unidades de medida em m
    val unidadesEmM = listOf(
        1_000.0,     // Km
        1.0,         // m
        0.1,         // dm
        0.01,        // cm
        0.001,       // mm
        1.0E-6,      // µm
        1.0E-9,      // nm
        1.0E-10,     // Å
        4_828.0,     // Liga (britânica)
        1_609.344,   // Milha (mi)
        201.168,     // Furlong
        20.1168,     // Corrente
        5.0292,      // Barra (rod)
        0.9144       // Jarda (yd)
    )

    // Primeiro o input do usuário é convertido para m
    // Depois o resultado é convertido para a unidade de saída
    // Essa abordagem evita o uso de condicionais para a conversão
    val valorEmM = variavel * unidadesEmM[itemPosition1]
    resultado = (valorEmM / unidadesEmM[itemPosition2]).toString()

    // Retorna o resultado formatado
    return resultado
}

// Conversão de dados digitais
fun convertDados(inputUnidade: String,      // Input do usuário
                 itemPosition1: Int,        // Unidade de entrada (de)
                 itemPosition2: Int         // Unidade de saída (para)
): String {
    /*
    Lista de unidades disponíveis
    [0] -> Bit (b)
    [1] -> Byte (B)
    [2] -> Kilobyte (KB)
    [3] -> Megabyte (MB)
    [4] -> Gigabyte (GB)
    [5] -> Terabyte (TB)
    [6] -> Petabyte (PB)
    [7] -> Exabyte (EB)
     */

    // Resultado da conversão
    var resultado: String = "0"
    // Tenta converter o input do usuário para número
    val variavel: Double = inputUnidade.toDoubleOrNull() ?: 0.0

    // Lista de unidades convertidas para megabytes (MB)
    val unidadesEmMB = listOf(
        1.0 / 8_000_000,       // Bit
        1.0 / 1_000_000,       // Byte
        1.0 / 1_000,           // KB
        1.0,                   // MB
        1_000.0,               // GB
        1_000_000.0,           // TB
        1_000_000_000.0,       // PB
        1_000_000_000_000.0    // EB
    )

    // Primeiro converte para MB, depois para unidade de saída
    val valorEmMB = variavel * unidadesEmMB[itemPosition1]
    resultado = (valorEmMB / unidadesEmMB[itemPosition2]).toString()

    // Retorna o resultado formatado
    return resultado
}

// Conversão de massa
fun convertMassa(inputUnidade: String,      // Input do usuário
                 itemPosition1: Int,        // Unidade de entrada (de)
                 itemPosition2: Int         // Unidade de saída (para)
): String {
    /*
    Lista de unidades disponíveis
    [0]  -> Tonelada (t)
    [1]  -> Kilonewton (kN)
    [2]  -> Quilograma (kg)
    [3]  -> Hectograma (hg)
    [4]  -> Decagrama (dag)
    [5]  -> Grama (g)
    [6]  -> Quilate
    [7]  -> Centigrama
    [8]  -> Miligrama (mg)
    [9]  -> Micrograma (µg)
    [10] -> Nanograma (ng)
    [11] -> Unidade de massa de átomo (u)
    [12] -> Libra (lb)
     */

    // Resultado da conversão
    var resultado: String = "0"
    // Tenta converter o input do usuário para número
    val variavel: Double = inputUnidade.toDoubleOrNull() ?: 0.0

    // Lista de unidades convertidas para quilogramas (kg)
    val unidadesEmKg = listOf(
        1_000.0,                    // Tonelada
        101.9716,                  // Kilonewton (aproximadamente, com g ≈ 9.80665)
        1.0,                       // Quilograma
        0.1,                       // Hectograma
        0.01,                      // Decagrama
        0.001,                     // Grama
        0.0002,                    // Quilate
        0.00001,                   // Centigrama
        1.0E-6,                    // Miligrama
        1.0E-9,                    // Micrograma
        1.0E-12,                   // Nanograma
        1.66053906660E-27,        // Unidade de massa atômica (u)
        0.45359237                 // Libra (lb)
    )

    // Primeiro o input do usuário é convertido para kg
    // Depois o resultado é convertido para a unidade de saída
    // Essa abordagem evita o uso de condicionais para a conversão
    val valorEmKg = variavel * unidadesEmKg[itemPosition1]
    resultado = (valorEmKg / unidadesEmKg[itemPosition2]).toString()

    // Retorna o resultado formatado
    return resultado
}

// Conversão pressão
fun convertPressao(inputUnidade: String,      // Input do usuário
                   itemPosition1: Int,        // Unidade de entrada (de)
                   itemPosition2: Int         // Unidade de saída (para)
): String {
    /*
    Lista de unidades disponíveis
    [0] -> Pascal (Pa)
    [1] -> Quilopascal (kPa)
    [2] -> Megapascal (MPa)
    [3] -> Bar (bar)
    [4] -> Milibar (mbar)
    [5] -> Atmosfera padrão (atm)
    [6] -> Torr (mmHg)
    [7] -> Libra por polegada quadrada (psi)
    [8] -> Quilograma-força por centímetro quadrado (kgf/cm²)
    [9] -> Atmosfera técnica (at)
    */

    // Resultado da conversão
    var resultado: String = "0"
    // Tenta converter o input do usuário para número
    val variavel: Double = inputUnidade.toDoubleOrNull() ?: 0.0

    // Lista de unidades convertidas para Pascal (Pa)
    val unidadesEmPa = listOf(
        1.0,                 // Pascal (Pa)
        1000.0,              // Quilopascal (kPa)
        1_000_000.0,         // Megapascal (MPa)
        100_000.0,           // Bar (bar)
        100.0,               // Milibar (mbar)
        101_325.0,           // Atmosfera padrão (atm)
        133.3223684,         // Torr (mmHg)
        6894.75729,          // Libra por polegada quadrada (psi)
        98066.5,             // Quilograma-força por centímetro quadrado (kgf/cm²)
        98066.5              // Atmosfera técnica (at) - igual a kgf/cm²
    )

    // Primeiro o input do usuário é convertido para Pa
    // Depois o resultado é convertido para a unidade de saída
    val valorEmPa = variavel * unidadesEmPa[itemPosition1]
    resultado = (valorEmPa / unidadesEmPa[itemPosition2]).toString()

    // Retorna o resultado formatado
    return resultado
}

// Conversão temperatura
fun convertTemperatura(inputUnidade: String,      // Input do usuário
                       itemPosition1: Int,        // Unidade de entrada (de)
                       itemPosition2: Int         // Unidade de saída (para)
): String {
    /*
    Lista de unidades disponíveis
    [0] -> Celsius (°C)
    [1] -> Fahrenheit (°F)
    [2] -> Kelvin (K)
    */

    // Resultado da conversão
    var resultado: String = "0"
    // Tenta converter o input do usuário para número
    val variavel: Double = inputUnidade.toDoubleOrNull() ?: 0.0

    // Tratamento especial para temperatura devido às fórmulas não lineares
    var valorEmCelsius: Double = 0.0

    // Converte a unidade de entrada para Celsius (base intermediária)
    when (itemPosition1) {
        0 -> valorEmCelsius = variavel // Celsius para Celsius
        1 -> valorEmCelsius = (variavel - 32.0) * 5.0 / 9.0 // Fahrenheit para Celsius
        2 -> valorEmCelsius = variavel - 273.15 // Kelvin para Celsius
    }

    // Converte de Celsius para a unidade de saída
    var valorFinal: Double = 0.0
    when (itemPosition2) {
        0 -> valorFinal = valorEmCelsius // Celsius para Celsius
        1 -> valorFinal = (valorEmCelsius * 9.0 / 5.0) + 32.0 // Celsius para Fahrenheit
        2 -> valorFinal = valorEmCelsius + 273.15 // Celsius para Kelvin
    }

    resultado = valorFinal.toString()

    // Retorna o resultado formatado
    return resultado
}

// Conversão tempo
fun convertTempo(inputUnidade: String,      // Input do usuário
                 itemPosition1: Int,        // Unidade de entrada (de)
                 itemPosition2: Int         // Unidade de saída (para)
): String {
    /*
    Lista de unidades disponíveis
    [0] -> Segundo (s)
    [1] -> Milissegundo (ms)
    [2] -> Microssegundo (µs)
    [3] -> Nanossegundo (ns)
    [4] -> Minuto (min)
    [5] -> Hora (h)
    [6] -> Dia (d - 24h)
    [7] -> Semana (wk)
    [8] -> Mês (aproximado - 30.4375 dias)
    [9] -> Ano (ano gregoriano - 365.2425 dias)
    [10] -> Década
    [11] -> Século
    */

    // Resultado da conversão
    var resultado: String = "0"
    // Tenta converter o input do usuário para número
    val variavel: Double = inputUnidade.toDoubleOrNull() ?: 0.0

    // Lista de unidades convertidas para Segundos (s)
    val unidadesEmS = listOf(
        1.0,                        // Segundo (s)
        0.001,                      // Milissegundo (ms)
        1.0E-6,                     // Microssegundo (µs)
        1.0E-9,                     // Nanossegundo (ns)
        60.0,                       // Minuto (min)
        3600.0,                     // Hora (h)
        86400.0,                    // Dia (d)
        604800.0,                   // Semana (wk)
        2_629_800.0,                // Mês (30.4375 * 86400)
        31_556_952.0,               // Ano Gregoriano (365.2425 * 86400)
        315_569_520.0,              // Década (10 * ano gregoriano)
        3_155_695_200.0             // Século (100 * ano gregoriano)
    )

    // Primeiro o input do usuário é convertido para s
    // Depois o resultado é convertido para a unidade de saída
    val valorEmS = variavel * unidadesEmS[itemPosition1]
    resultado = (valorEmS / unidadesEmS[itemPosition2]).toString()

    // Retorna o resultado formatado
    return resultado
}

// Conversão velocidade
fun convertVelocidade(inputUnidade: String,      // Input do usuário
                      itemPosition1: Int,        // Unidade de entrada (de)
                      itemPosition2: Int         // Unidade de saída (para)
): String {
    /*
    Lista de unidades disponíveis
    [0] -> Metro por segundo (m/s)
    [1] -> Quilômetro por hora (km/h)
    [2] -> Milha por hora (mph)
    [3] -> Pé por segundo (ft/s)
    [4] -> Nó (knot - milha náutica por hora)
    [5] -> Velocidade da luz no vácuo (c)
    [6] -> Mach (à temperatura e pressão padrão ao nível do mar ≈ 343 m/s)
    */

    // Resultado da conversão
    var resultado: String = "0"
    // Tenta converter o input do usuário para número
    val variavel: Double = inputUnidade.toDoubleOrNull() ?: 0.0

    // Lista de unidades convertidas para Metros por segundo (m/s)
    val unidadesEmMs = listOf(
        1.0,                       // Metro por segundo (m/s)
        1.0 / 3.6,                 // Quilômetro por hora (km/h) -> m/s
        0.44704,                   // Milha por hora (mph) -> m/s (1609.344 / 3600)
        0.3048,                    // Pé por segundo (ft/s) -> m/s
        0.514444,                  // Nó (knot) -> m/s (1852 / 3600)
        299_792_458.0,             // Velocidade da luz (c) -> m/s
        343.0                      // Mach (aproximado em condições padrão) -> m/s
    )

    // Primeiro o input do usuário é convertido para m/s
    // Depois o resultado é convertido para a unidade de saída
    val valorEmMs = variavel * unidadesEmMs[itemPosition1]
    resultado = (valorEmMs / unidadesEmMs[itemPosition2]).toString()

    // Retorna o resultado formatado
    return resultado
}

// Conversão volume
fun convertVolume(inputUnidade: String,      // Input do usuário
                  itemPosition1: Int,        // Unidade de entrada (de)
                  itemPosition2: Int         // Unidade de saída (para)
): String {
    /*
    Lista de unidades disponíveis
    [0] -> Metro cúbico (m³)
    [1] -> Litro (L)
    [2] -> Mililitro (mL) / Centímetro cúbico (cm³)
    [3] -> Galão (US liquid)
    [4] -> Quarto (US liquid - quart)
    [5] -> Pinto (US liquid - pint)
    [6] -> Onça líquida (US fluid ounce - fl oz)
    [7] -> Galão (Imperial - UK)
    [8] -> Quarto (Imperial - UK)
    [9] -> Pinto (Imperial - UK)
    [10] -> Onça líquida (Imperial - UK fl oz)
    [11] -> Pé cúbico (ft³)
    [12] -> Polegada cúbica (in³)
    [13] -> Barril (US oil - bbl)
    [14] -> Colher de sopa (US tbsp - approx 14.8 mL)
    [15] -> Colher de chá (US tsp - approx 4.9 mL)
    */

    // Resultado da conversão
    var resultado: String = "0"
    // Tenta converter o input do usuário para número
    val variavel: Double = inputUnidade.toDoubleOrNull() ?: 0.0

    // Lista de unidades convertidas para Litros (L)
    val unidadesEmL = listOf(
        1000.0,             // Metro cúbico (m³)
        1.0,                // Litro (L)
        0.001,              // Mililitro (mL) / cm³
        3.78541,            // Galão (US liquid)
        0.946353,           // Quarto (US liquid - 1/4 gal)
        0.473176,           // Pinto (US liquid - 1/8 gal)
        0.0295735,          // Onça líquida (US fl oz - 1/128 gal)
        4.54609,            // Galão (Imperial - UK)
        1.1365225,          // Quarto (Imperial - 1/4 gal)
        0.56826125,         // Pinto (Imperial - 1/8 gal)
        0.0284130625,       // Onça líquida (Imperial - 1/160 gal)
        28.3168,            // Pé cúbico (ft³)
        0.0163871,          // Polegada cúbica (in³)
        158.987,            // Barril (US oil - 42 US gal)
        0.0147868,          // Colher de sopa (US tbsp - approx)
        0.00492892          // Colher de chá (US tsp - approx)
    )

    // Primeiro o input do usuário é convertido para L
    // Depois o resultado é convertido para a unidade de saída
    val valorEmL = variavel * unidadesEmL[itemPosition1]
    resultado = (valorEmL / unidadesEmL[itemPosition2]).toString()

    // Retorna o resultado formatado
    return resultado
}
//--------------------------------------------------------------------------------------------------
// Funções ultilitárias

//--------------------------------------------------------------------------------------------------
// Início do código principal:
// Classe principal da activity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConverterTheme {
                // Inicializa o aplicativo
                MainApp()
            }
        }
    }
}

// Composable prinicipal do aplicativo
@Composable
fun MainApp (modifier: Modifier = Modifier) {
    // Cria o NavController que gerencia o estado da navegação
    val navController = rememberNavController()
    Scaffold {innerPadding ->
        // Cria o NavHost que atua como container na exibição da interface gráfica
        NavHost (
            navController = navController,          // Passa a instância do NavController
            startDestination = Screen.Home.name,    // Define a rota inicial de navegação
            modifier = modifier.padding(innerPadding)
        ) {
            // Mapeia a rota Home para o composable HomeScreen
            composable (route = Screen.Home.name) {
                // Chama o composable da tela inicial
                HomeScreen(
                    onNavigateToArea = {navController.navigate(Screen.Area.name)},
                    onNavigateToTemperatura = {navController.navigate(Screen.Temperatura.name)},
                    onNavigateToComprimento = {navController.navigate(Screen.Comprimento.name)},
                    onNavigateToDados = {navController.navigate(Screen.Dados.name)},
                    onNavigateToMassa = {navController.navigate(Screen.Massa.name)},
                    onNavigateToPressao = {navController.navigate(Screen.Pressao.name)},
                    onNavigateToTempo = {navController.navigate(Screen.Tempo.name)},
                    onNavigateToVelocidade = {navController.navigate(Screen.Velocidade.name)},
                    onNavigateToVolume = {navController.navigate(Screen.Volume.name)},
                )
            }
            // Mapeia a rota Area para o composable AreaScreen
            composable (route = Screen.Area.name) {
                // Chama o composable da tela de conversão de área
                AreaScreen (onGoBack = {navController.navigateUp()})
            }
            // Mapeia a rota Comprimento para o composable ComprimentoScreen
            composable (route = Screen.Comprimento.name) {
                // Chama o composable da tela de conversão de comprimento
                UnitScreen(modifier = modifier,
                    onGoBack = {navController.navigateUp()},
                    titulo = "comprimento",
                    unidades = listOf<String>(
                        "Quilômetro (km)",
                        "Metro (m)",
                        "Decímetro (dm)",
                        "Centímetro (cm)",
                        "Milímetro (mm)",
                        "Micrômetro (µm)",
                        "Nanômetro (nm)",
                        "Angstrom (Å)",
                        "Liga (britânica)",
                        "Milha (mi)",
                        "Furlong",
                        "Corrente",
                        "Barra (rod)",
                        "Jarda (yd)"
                    ),
                    tipo_conversao = 2
                )
            }
            // Mapeia a rota Dados para o composable DadosScreen
            composable (route = Screen.Dados.name) {
                // Chama o composable da tela de conversão de dados
                UnitScreen(modifier = modifier,
                    onGoBack = {navController.navigateUp()},
                    titulo = "dados",
                    unidades =  listOf<String>(
                    "Bit (b)",
                    "Byte (B)",
                    "Kilobyte (KB)",
                    "Megabyte (MB)",
                    "Gigabyte (GB)",
                    "Terabyte (TB)",
                    "Petabyte (PB)",
                    "Exabyte (EB)"
                ),
                    tipo_conversao = 3
                )
            }
            // Mapeia a rota Massa para o composable MassaScreen
            composable (route = Screen.Massa.name) {
                // Chama o composable da tela de conversão de massa
                UnitScreen(modifier = modifier,
                    onGoBack = {navController.navigateUp()},
                    titulo = "massa",
                    unidades = listOf<String>(
                        "Tonelada (t)",
                        "Kilonewton (kN)",
                        "Quilograma (kg)",
                        "Hectograma (hg)",
                        "Decagrama (dag)",
                        "Grama (g)",
                        "Quilate",
                        "Centigrama",
                        "Miligrama (mg)",
                        "Micrograma (µg)",
                        "Nanograma (ng)",
                        "Unidade de massa de átomo (u)",
                        "Libra (lb)"
                    ),
                    tipo_conversao = 4
                )
            }
            // Mapeia a rota Pressao para o composable PressaoScreen
            composable (route = Screen.Pressao.name) {
                // Chama o composable da tela de conversão de pressão
                UnitScreen(modifier = modifier,
                    onGoBack = {navController.navigateUp()},
                    titulo = "pressão",
                    unidades = listOf<String>(
                        "Pascal (Pa)",
                        "Quilopascal (kPa)",
                        "Megapascal (MPa)",
                        "Bar (bar)",
                        "Milibar (mbar)",
                        "Atmosfera padrão (atm)",
                        "Torr (mmHg)",
                        "Libra por polegada quadrada (psi)",
                        "Quilograma-força por centímetro quadrado (kgf/cm²)",
                        "Atmosfera técnica (at)"
                    ),
                    tipo_conversao = 5
                )
            }
            // Mapeia a rota Temperatura para o composable TemperaturaScreen
            composable (route = Screen.Temperatura.name) {
                // Chama o composable da tela de conversão de temperatura
                UnitScreen(modifier = modifier,
                    onGoBack = {navController.navigateUp()},
                    titulo = "temperatura",
                    unidades = listOf<String>(
                        "Celsius (°C)",
                        "Fahrenheit (°F)",
                        "Kelvin (K)"
                    ),
                    tipo_conversao = 6
                )
            }
            // Mapeia a rota Tempo para o composable TempoScreen
            composable (route = Screen.Tempo.name) {
                // Chama o composable da tela de conversão de tempo
                UnitScreen(modifier = modifier,
                    onGoBack = {navController.navigateUp()},
                    titulo = "tempo",
                    unidades = listOf<String>(
                        "Segundo (s)",
                        "Milissegundo (ms)",
                        "Microssegundo (µs)",
                        "Nanossegundo (ns)",
                        "Minuto (min)",
                        "Hora (h)",
                        "Dia",
                        "Semana",
                        "Mês",
                        "Ano",
                        "Década",
                        "Século"
                    ),
                    tipo_conversao = 7
                )
            }
            // Mapeia a rota Velocidade para o composable VelocidadeScreen
            composable (route = Screen.Velocidade.name) {
                // Chama o composable da tela de conversão de velocidade
                UnitScreen(modifier = modifier,
                    onGoBack = {navController.navigateUp()},
                    titulo = "velocidade",
                    unidades = listOf<String>(
                        "Metro por segundo (m/s)",
                        "Quilômetro por hora (km/h)",
                        "Milha por hora (mph)",
                        "Pé por segundo (ft/s)",
                        "Nó (knot - milha náutica por hora)",
                        "Velocidade da luz no vácuo (c)",
                        "Mach"
                    ),
                    tipo_conversao = 8
                )
            }
            // Mapeia a rota Volume para o composable VolumeScreen
            composable (route = Screen.Volume.name) {
                // Chama o composable da tela de conversão de volume
                UnitScreen(modifier = modifier,
                    onGoBack = {navController.navigateUp()},
                    titulo = "volume",
                    unidades = listOf<String>(
                        "Metro cúbico (m³)",
                        "Litro (L)",
                        "Mililitro (mL) / Centímetro cúbico (cm³)",
                        "Galão (US liquid)",
                        "Quarto (US liquid - quart)",
                        "Pinto (US liquid - pint)",
                        "Onça líquida (US fluid ounce - fl oz)",
                        "Galão (Imperial - UK)",
                        "Quarto (Imperial - UK)",
                        "Pinto (Imperial - UK)",
                        "Onça líquida (Imperial - UK fl oz)",
                        "Pé cúbico (ft³)",
                        "Polegada cúbica (in³)",
                        "Barril (US oil - bbl)",
                        "Colher de sopa (US tbsp - approx 14.8 mL)",
                        "Colher de chá (US tsp - approx 4.9 mL)"
                    ),
                    tipo_conversao = 9
                )
            }
        }
    }
}

// Composable que desenha a tela inicial do aplicativo
@Composable
fun HomeScreen(
    // Parâmetros chamados pelo MainApp para acesso às telas de conversão
    onNavigateToArea: () -> Unit,
    onNavigateToComprimento: () -> Unit,
    onNavigateToDados: () -> Unit,
    onNavigateToMassa: () -> Unit,
    onNavigateToPressao: () -> Unit,
    onNavigateToTemperatura: () -> Unit,
    onNavigateToTempo: () -> Unit,
    onNavigateToVelocidade: () -> Unit,
    onNavigateToVolume: () -> Unit,
) {
    // Coluna principal de sustentação do aplicativo
    Column (modifier = Modifier
        .fillMaxSize()
        .border(3.dp, Color.Blue),) {
        // Sucessão de linhas em que serão disponibilizados os botões que levam para as telas de conversão
        // Primeira linha
        Row () {
            // Área
            Button(onClick = {onNavigateToArea()}) {
                Text(text = "Área")
            }
            // Comprimento
            Button(onClick = {onNavigateToComprimento()}) {
                Text(text = "Comprimento")
            }
            // Dados
            Button(onClick = {onNavigateToDados()}) {
                Text(text = "Dados ")
            }
        }
        // Segunda linha
        Row () {
            // Massa
            Button(onClick = {onNavigateToMassa()}) {
                Text(text = "Massa")
            }
            // Pressão
            Button(onClick = {onNavigateToPressao()}) {
                Text(text = "Pressão")
            }
            // Temperatura
            Button(onClick = {onNavigateToTemperatura()}) {
                Text(text = "Temperatura")
            }
        }
        // Terceira linha
        Row () {
            // Tempo
            Button(onClick = {onNavigateToTempo()}) {
                Text(text = "Tempo")
            }
            // Velocidade
            Button(onClick = {onNavigateToVelocidade()}) {
                Text(text = "Velocidade")
            }
            // Volume
            Button(onClick = {onNavigateToVolume()}) {
                Text(text = "Volume")
            }
        }
    }
}

// Tela de conversão de área
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AreaScreen(
    modifier: Modifier = Modifier,      // Modificador para customização da tela
    onGoBack: () -> Unit                // Função de retorno à tela anterior
) {
    // Unidades de medida disponíveis
    val unidades = listOf(
        "Quilômetro quadrado (km²)",
        "Hectare (ha)",
        "Are (a)",
        "Metro quadrado (m²)",
        "Decímetro quadrado (dm²)",
        "Centímetro quadrado (cm²)",
        "Milímetro quadrado (mm²)",
        "Micrômetro quadrado (µm²)",
        "Nanômetro quadrado (nm²)"
    )

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
            TopAppBar (title = { Text(text = "Converter área") },
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
                    Text(
                        text = convertArea(
                            inputUnidade = inputUnidade,
                            itemPosition1 = itemPosition1.value,
                            itemPosition2 = itemPosition2.value),
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

// Tela de conversão de comprimento
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComprimentoScreen(
    modifier: Modifier = Modifier,      // Modificador para customização da tela
    onGoBack: () -> Unit                // Função de retorno à tela anterior
) {
    UnitScreen(modifier = modifier, onGoBack = onGoBack, titulo = "Comprimento",
        unidades = listOf("Unidade 1", "Unidade 2", "Unidade 3"),
        tipo_conversao = 2)
}

// Tela de conversão de dados
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DadosScreen(
    modifier: Modifier = Modifier,      // Modificador para customização da tela
    onGoBack: () -> Unit                // Função de retorno à tela anterior
) {
    // Unidades de medida disponíveis
    val unidades = listOf(
        "unidades"
    )

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
            TopAppBar (title = { Text(text = "Converter dados") },
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
                    Text(
                        text = convertArea(
                            inputUnidade = inputUnidade,
                            itemPosition1 = itemPosition1.value,
                            itemPosition2 = itemPosition2.value),
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

// Tela de conversão de massa
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MassaScreen(
    modifier: Modifier = Modifier,      // Modificador para customização da tela
    onGoBack: () -> Unit                // Função de retorno à tela anterior
) {
    // Unidades de medida disponíveis
    val unidades = listOf(
        "unidades"
    )

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
            TopAppBar (title = { Text(text = "Converter massa") },
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
                    Text(
                        text = convertArea(
                            inputUnidade = inputUnidade,
                            itemPosition1 = itemPosition1.value,
                            itemPosition2 = itemPosition2.value),
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun PressaoScreen(modifier: Modifier = Modifier,
                  onGoBack: () -> Unit) {
    Text (text = "Pressao Screen")
}

@Composable
fun TemperaturaScreen(modifier: Modifier = Modifier,
                      onGoBack: () -> Unit) {
    Text (text = "Temperatura Screen")
}

@Composable
fun TempoScreen(modifier: Modifier = Modifier,
                onGoBack: () -> Unit) {
    Text (text = "Tempo Screen")
}

@Composable
fun VelocidadeScreen(modifier: Modifier = Modifier,
                     onGoBack: () -> Unit) {
    Text (text = "Velocidade Screen")
}

@Composable
fun VolumeScreen(modifier: Modifier = Modifier,
                 onGoBack: () -> Unit) {
    Text (text = "Volume Screen")
}

/*
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ConverterTheme {
        MainApp()
    }
}
 */

@Preview (showBackground = true)
@Composable
fun AreaScreenPreview() {
    AreaScreen(modifier = Modifier,
        onGoBack = {})
}