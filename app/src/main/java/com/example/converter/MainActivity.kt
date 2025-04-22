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

enum class Screen {
    Home,
    Temperature
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConverterTheme {
                MainApp()
                }
            }
        }
    }

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
                    onNavigateToTemperature = {navController.navigate(Screen.Temperature.name)}
                )
            }
            // Mapeia a rota Temperature para o composable TemperatureScreen
            composable (route = Screen.Temperature.name) {
                // Chama o composable da tela de conversão de temperatura
                TemperatureScreen (onGoBack = {navController.navigateUp()})
            }
        }
    }
}

// Composable que desenha a tela inicial do aplicativo
@Composable
fun HomeScreen(
    onNavigateToTemperature: () -> Unit,    // Função a ser executada ao clicar no botão
    modifier: Modifier = Modifier           // Modificador para personalizar a exibição
) {
    Column (modifier = Modifier
        .fillMaxSize()
        .border(3.dp, Color.Red),){

    }
}

@Composable
fun TemperatureScreen(modifier: Modifier = Modifier,
                      onGoBack: () -> Unit) {
    Text (text = "Temperature Screen")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ConverterTheme {
        MainApp()
    }
}