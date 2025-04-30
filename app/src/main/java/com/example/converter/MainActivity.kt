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
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign

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
                UnitScreen(modifier = modifier,
                    onGoBack = {navController.navigateUp()},
                    titulo = "área",
                    unidades = listOf(
                        "Quilômetro quadrado (km²)",
                        "Hectare (ha)",
                        "Are (a)",
                        "Metro quadrado (m²)",
                        "Decímetro quadrado (dm²)",
                        "Centímetro quadrado (cm²)",
                        "Milímetro quadrado (mm²)",
                        "Micrômetro quadrado (µm²)",
                        "Nanômetro quadrado (nm²)"
                    ),
                    tipo_conversao = 1
                )
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
    // Lista de opções de conversão (Label e Ação)
    val conversionItems = listOf(
        "Área" to onNavigateToArea,
        "Comprimento" to onNavigateToComprimento,
        "Dados" to onNavigateToDados,
        "Massa" to onNavigateToMassa,
        "Pressão" to onNavigateToPressao,
        "Temperatura" to onNavigateToTemperatura,
        "Tempo" to onNavigateToTempo,
        "Velocidade" to onNavigateToVelocidade,
        "Volume" to onNavigateToVolume
    )

    // Coluna principal de sustentação do aplicativo
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Padding geral da tela
        horizontalAlignment = Alignment.CenterHorizontally, // Centraliza horizontalmente
    ) {
        // Título da Tela
        Text (
            text = "Selecione o tipo de conversão:",
            style = MaterialTheme.typography.headlineSmall, // Estilo do título
            modifier = Modifier.padding(bottom = 16.dp) ,// Espaço abaixo do título
        )

        // Grade Vertical para os itens de conversão
        LazyVerticalGrid(
            columns = GridCells.Fixed(3), // Define 3 colunas
            horizontalArrangement = Arrangement.spacedBy(8.dp), // Espaço horizontal entre cards
            verticalArrangement = Arrangement.spacedBy(8.dp),   // Espaço vertical entre cards
            modifier = Modifier.fillMaxSize() // Ocupa o espaço restante
        ) {
            // Cria um card para cada item na lista conversionItems
            items(conversionItems) { (label, navigateAction) ->
                ConversionTypeCard (
                    label = label,
                    // Passa a ação de navegação para o parâmetro onClick (convenção corrigida)
                    onClick = navigateAction
                )
            }
        }
    }
}

// Card individual para cada tipo de conversão na grade
@OptIn(ExperimentalMaterial3Api::class) // Necessário para Card onClick
@Composable
fun ConversionTypeCard (
    label: String,
    onClick: () -> Unit, // <<-- Parâmetro renomeado para onClick (convenção)
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick, // <<-- Usa o parâmetro onClick
        modifier = modifier
            // .aspectRatio(1f) // Descomente se quiser cards quadrados
            .padding(4.dp), // Pequeno espaçamento externo para o card não colar nos outros
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), // Elevação/sombra
    ) {
        // Conteúdo dentro do Card
        Column(
            modifier = Modifier
                .fillMaxSize() // Ocupa  o espaço do Card completo
                .padding(8.dp), // Padding interno
            horizontalAlignment = Alignment.CenterHorizontally, // Centraliza horizontalmente
            verticalArrangement = Arrangement.Center // Centraliza verticalmente
        ) {
            // Ícone representativo (substitua pelos ícones corretos)
            Icon(
                painter = when(label) {
                    "Área" -> painterResource(R.drawable.icon_area)
                    "Comprimento" -> painterResource(R.drawable.icon_comprimento)
                    "Dados" -> painterResource(R.drawable.icon_data)
                    "Massa" -> painterResource(R.drawable.icon_massa)
                    "Pressão" -> painterResource(R.drawable.icon_pressao)
                    "Tempo" -> painterResource(R.drawable.icon_tempo)
                    "Volume" -> painterResource(R.drawable.icon_volume)
                    "Temperatura" -> painterResource(R.drawable.icon_temperature)
                    "Velocidade" -> painterResource(R.drawable.icon_velocidade)
                    else -> painterResource(R.drawable.ic_launcher_background)
                },
                contentDescription = null, // O texto já descreve a ação
                modifier = Modifier.size(40.dp), // Tamanho do ícone (ajuste se necessário)
                tint = MaterialTheme.colorScheme.primary // Usa a cor primária do tema
            )
            // Espaçador entre Ícone e Texto
            Spacer(modifier = Modifier.height(8.dp))
            // Texto com o nome do tipo de conversão
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium, // Estilo do texto
                textAlign = TextAlign.Center // Garante centralização se o texto quebrar linha
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ConverterTheme {
        MainApp()
    }
}