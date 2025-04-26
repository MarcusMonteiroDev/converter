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
fun ModeloMenu (isDropDownExpanded: MutableState<Boolean>,
                itemPosition: MutableState<Int>,
                opcoes: List<String>) {
    Row (
        modifier = Modifier.clickable {
            // Ao clicar, inverte o estado do DropdownMenu (abre ou fecha)
            isDropDownExpanded.value = true
        }
    ) {
        // Exibe o texto da opção selecionada com base no índice armazenado em itemPosition
        Text(text = opcoes[itemPosition.value])

        // DropdownMenu para exibir a lista de opções
        DropdownMenu(
            expanded = isDropDownExpanded.value, // Controla a visibilidade do menu
            onDismissRequest = {
                // Fecha o menu quando o usuário clica fora dele
                isDropDownExpanded.value = false
            }) {
            // Itera sobre a lista de opções e seus índices
            opcoes.forEachIndexed { index, item ->
                // Cria um DropdownMenuItem para cada opção
                DropdownMenuItem(text = {
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
//--------------------------------------------------------------------------------------------------
// Funções ultilitárias
// Corretor de string vazia: retorna "0" caso seja passado para ele uma string vazia
fun corretorStringVazia (input: String): String {
    if (input == "") {
        return "0"
    }
    return input
}
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
                ComprimentoScreen (onGoBack = {navController.navigateUp()})
            }
            // Mapeia a rota Dados para o composable DadosScreen
            composable (route = Screen.Dados.name) {
                // Chama o composable da tela de conversão de dados
                DadosScreen (onGoBack = {navController.navigateUp()})
            }
            // Mapeia a rota Massa para o composable MassaScreen
            composable (route = Screen.Massa.name) {
                // Chama o composable da tela de conversão de massa
                MassaScreen (onGoBack = {navController.navigateUp()})
            }
            // Mapeia a rota Pressao para o composable PressaoScreen
            composable (route = Screen.Pressao.name) {
                // Chama o composable da tela de conversão de pressão
                PressaoScreen (onGoBack = {navController.navigateUp()})
            }
            // Mapeia a rota Temperatura para o composable TemperaturaScreen
            composable (route = Screen.Temperatura.name) {
                // Chama o composable da tela de conversão de temperatura
                TemperaturaScreen (onGoBack = {navController.navigateUp()})
            }
            // Mapeia a rota Tempo para o composable TempoScreen
            composable (route = Screen.Tempo.name) {
                // Chama o composable da tela de conversão de tempo
                TempoScreen (onGoBack = {navController.navigateUp()})
            }
            // Mapeia a rota Velocidade para o composable VelocidadeScreen
            composable (route = Screen.Velocidade.name) {
                // Chama o composable da tela de conversão de velocidade
                VelocidadeScreen (onGoBack = {navController.navigateUp()})
            }
            // Mapeia a rota Volume para o composable VolumeScreen
            composable (route = Screen.Volume.name) {
                // Chama o composable da tela de conversão de volume
                VolumeScreen (onGoBack = {navController.navigateUp()})
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
        .border(3.dp, Color.Red),) {
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
@Composable
fun AreaScreen(modifier: Modifier = Modifier,
               onGoBack: () -> Unit) {
    // Unidades de medida de área disponíveis
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

    val itemPosition1 = remember { mutableStateOf(0) }
    val itemPosition2 = remember { mutableStateOf(1) }

    // Coluna principal de sustentação da tela
    Column (modifier = Modifier.fillMaxSize()) {
        Text (text = "Converter de")

        // Menu expandido (DropDownMenu) com as opções de unidades de medida
        ModeloMenu (isDropDownExpanded = remember { mutableStateOf(false) },
            itemPosition = itemPosition1,
            opcoes = unidades)

        TextField (value = inputUnidade,
            onValueChange = {inputUnidade = it},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(text = "Valor")},
            singleLine = true
        )

        Text (text = "Para:")

        ModeloMenu (isDropDownExpanded = remember { mutableStateOf(false) },
            itemPosition = itemPosition2,
            opcoes = unidades)

        Text (text = "Resultado:")

        Text (text = convertArea(inputUnidade = inputUnidade,
            itemPosition1 = itemPosition1.value,
            itemPosition2 = itemPosition2.value))
    }
}

@Composable
fun ComprimentoScreen(modifier: Modifier = Modifier,
                      onGoBack: () -> Unit) {
    Text (text = "Comprimento Screen")
}

@Composable
fun DadosScreen(modifier: Modifier = Modifier,
                onGoBack: () -> Unit) {
    Text (text = "Dados Screen")
}

@Composable
fun MassaScreen(modifier: Modifier = Modifier,
                onGoBack: () -> Unit) {
    Text (text = "Massa Screen")
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