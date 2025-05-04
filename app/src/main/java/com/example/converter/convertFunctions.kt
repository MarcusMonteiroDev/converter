package com.example.converter

import kotlin.math.PI
import java.util.Locale

// Funções ultilitárias
// Formatação da string do resultado
fun formatarResultado (resultado: String): String {
    val numero = resultado.toDouble()

    if (numero >= 1.0e7) {
        return String.format(Locale("pt","BR"), "%.2E", numero)
    }
    if (numero < 0.001 && numero != 0.0) {
        return String.format(Locale("pt","BR"), "%.2E", numero)
    }
    return numero.toString()
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

    // Variável usada no cálculo. Tenta converter o input do usuário para um número
    // caso não consiga, retorna 0
    // Também substitui a vírgula por ponto para evitar erros de conversão
    val textoParaParse = inputUnidade.replace(',', '.')
    var variavel: Double = textoParaParse.toDoubleOrNull() ?: 0.0

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
    return formatarResultado(resultado)
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
    // Variável usada no cálculo. Tenta converter o input do usuário para um número
    // caso não consiga, retorna 0
    // Também substitui a vírgula por ponto para evitar erros de conversão
    val textoParaParse = inputUnidade.replace(',', '.')
    var variavel: Double = textoParaParse.toDoubleOrNull() ?: 0.0

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
    return formatarResultado(resultado)
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
    // Variável usada no cálculo. Tenta converter o input do usuário para um número
    // caso não consiga, retorna 0
    // Também substitui a vírgula por ponto para evitar erros de conversão
    val textoParaParse = inputUnidade.replace(',', '.')
    var variavel: Double = textoParaParse.toDoubleOrNull() ?: 0.0

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
    return formatarResultado(resultado)
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
    // Variável usada no cálculo. Tenta converter o input do usuário para um número
    // caso não consiga, retorna 0
    // Também substitui a vírgula por ponto para evitar erros de conversão
    val textoParaParse = inputUnidade.replace(',', '.')
    var variavel: Double = textoParaParse.toDoubleOrNull() ?: 0.0

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
    return formatarResultado(resultado)
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
    // Variável usada no cálculo. Tenta converter o input do usuário para um número
    // caso não consiga, retorna 0
    // Também substitui a vírgula por ponto para evitar erros de conversão
    val textoParaParse = inputUnidade.replace(',', '.')
    var variavel: Double = textoParaParse.toDoubleOrNull() ?: 0.0

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
    return formatarResultado(resultado)
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
    // Variável usada no cálculo. Tenta converter o input do usuário para um número
    // caso não consiga, retorna 0
    // Também substitui a vírgula por ponto para evitar erros de conversão
    val textoParaParse = inputUnidade.replace(',', '.')
    var variavel: Double = textoParaParse.toDoubleOrNull() ?: 0.0

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
    return formatarResultado(resultado)
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
    // Variável usada no cálculo. Tenta converter o input do usuário para um número
    // caso não consiga, retorna 0
    // Também substitui a vírgula por ponto para evitar erros de conversão
    val textoParaParse = inputUnidade.replace(',', '.')
    var variavel: Double = textoParaParse.toDoubleOrNull() ?: 0.0

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
    return formatarResultado(resultado)
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
    // Variável usada no cálculo. Tenta converter o input do usuário para um número
    // caso não consiga, retorna 0
    // Também substitui a vírgula por ponto para evitar erros de conversão
    val textoParaParse = inputUnidade.replace(',', '.')
    var variavel: Double = textoParaParse.toDoubleOrNull() ?: 0.0

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
    return formatarResultado(resultado)
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
    // Variável usada no cálculo. Tenta converter o input do usuário para um número
    // caso não consiga, retorna 0
    // Também substitui a vírgula por ponto para evitar erros de conversão
    val textoParaParse = inputUnidade.replace(',', '.')
    var variavel: Double = textoParaParse.toDoubleOrNull() ?: 0.0

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
    return formatarResultado(resultado)
}

// Conversão Energia
fun convertEnergia (inputUnidade: String,      // Input do usuário
                    itemPosition1: Int,        // Unidade de entrada (de)
                    itemPosition2: Int         // Unidade de saída (para)
): String {
    /*
    Lista de unidades disponíveis
    [0] -> Joule (J)
    [1] -> Caloria (cal)
    [2] -> Quilocaloria (kcal / Cal)
    [3] -> Quilowatt-hora (kWh)
    [4] -> Elétron-volt (eV)
    [5] -> BTU (British Thermal Unit)
    [6] -> Pé-libra (ft-lb)
    */

    // Resultado da conversão
    var resultado: String = "0"
    // Variável usada no cálculo. Tenta converter o input do usuário para um número
    // caso não consiga, retorna 0
    // Também substitui a vírgula por ponto para evitar erros de conversão
    val textoParaParse = inputUnidade.replace(',', '.')
    var variavel: Double = textoParaParse.toDoubleOrNull() ?: 0.0

    // Lista de unidades convertidas para Joules (J)
    val unidadesEmJ = listOf(
        1.0,                       // Joule (J)
        4.184,                     // Caloria (cal) - termoquímica
        4184.0,                    // Quilocaloria (kcal / Cal) - termoquímica
        3_600_000.0,               // Quilowatt-hora (kWh) - 3.6E6
        1.602176634E-19,           // Elétron-volt (eV) - valor exato CODATA 2018
        1055.056,                  // BTU (British Thermal Unit) - ISO 31-4
        1.3558179483314004         // Pé-libra (ft-lb)
    )

    // Primeiro o input do usuário é convertido para J (Joules)
    // Depois o resultado é convertido para a unidade de saída
    val valorEmJ = variavel * unidadesEmJ[itemPosition1]
    resultado = (valorEmJ / unidadesEmJ[itemPosition2]).toString()

    // Retorna o resultado formatado
    return formatarResultado(resultado)
}

// Conversão Potência
fun convertPotencia (inputUnidade: String,      // Input do usuário
                    itemPosition1: Int,        // Unidade de entrada (de)
                    itemPosition2: Int         // Unidade de saída (para)
): String {
    /*
    Lista de unidades disponíveis (dBm omitido por ser logarítmico)
    [0] -> Miliwatt
    [1] -> Watt (W)
    [2] -> Kilowatt (kW)
    [3] -> Megawatt (MW)
    [4] -> Joule por segundo (J/s)
    [5] -> Horse-power (hp)
    [6] -> Cavalo-vapor
    [7] -> Cavalo-de-força elétrico
    [8] -> Cavalo boiler
    [9] -> Pés-libra por minuto
    [10] -> Pés-libra por segundo
    [11] -> Calorias por hora
    */

    // Resultado da conversão
    var resultado: String = "0"
    // Variável usada no cálculo. Tenta converter o input do usuário para um número
    // caso não consiga, retorna 0
    // Também substitui a vírgula por ponto para evitar erros de conversão
    val textoParaParse = inputUnidade.replace(',', '.')
    var variavel: Double = textoParaParse.toDoubleOrNull() ?: 0.0

    // Lista de unidades convertidas para Watts (W)
    // (Fatores de conversão aproximados onde aplicável)
    val unidadesEmW = listOf(
        0.001,                     // Miliwatt
        1.0,                       // Watt (W)
        1000.0,                    // Kilowatt (kW)
        1_000_000.0,               // Megawatt (MW)
        1.0,                       // Joule por segundo (J/s) - é igual a Watt
        745.6998715822702,         // Horse-power (hp) - mecânico (550 ft⋅lbf/s)
        735.49875,                 // Cavalo-vapor (CV) - métrico
        746.0,                     // Cavalo-de-força elétrico
        9809.5,                    // Cavalo boiler
        0.0225969658,              // Pés-libra por minuto (ft·lbf/min) -> W (1.3558179 / 60)
        1.3558179483,              // Pés-libra por segundo (ft·lbf/s) -> W
        0.001162222                // Calorias por hora (cal/h) -> W (4.184 / 3600) - termoquímica
    )

    // Primeiro o input do usuário é convertido para W (Watts)
    // Depois o resultado é convertido para a unidade de saída
    val valorEmW = variavel * unidadesEmW[itemPosition1]
    resultado = (valorEmW / unidadesEmW[itemPosition2]).toString()

    // Retorna o resultado formatado
    return formatarResultado(resultado)
}

// Conversão Força
fun convertForca (inputUnidade: String,      // Input do usuário
                 itemPosition1: Int,        // Unidade de entrada (de)
                 itemPosition2: Int         // Unidade de saída (para)
): String {
    /*
    Lista de unidades disponíveis
    [0] -> Nanonewton (nN)
    [1] -> Micronewton (µN)
    [2] -> Mili Newton (mN)
    [3] -> Newton (N)
    [4] -> Quilonewton (kN)
    [5] -> Meganewton (MN)
    [6] -> Giganewton (GN)
    [7] -> Dina (dyn)
    [8] -> Joule por metro (J/m)
    [9] -> Kilograma Força (kgf)
    [10] -> Tonelada-força (tnf)
    */

    // Resultado da conversão
    var resultado: String = "0"
    // Variável usada no cálculo. Tenta converter o input do usuário para um número
    // caso não consiga, retorna 0
    // Também substitui a vírgula por ponto para evitar erros de conversão
    val textoParaParse = inputUnidade.replace(',', '.')
    var variavel: Double = textoParaParse.toDoubleOrNull() ?: 0.0

    // Lista de unidades convertidas para Newtons (N)
    // (Fator para kgf e tnf usa g ≈ 9.80665 m/s²)
    val unidadesEmN = listOf(
        1.0E-9,             // Nanonewton (nN)
        1.0E-6,             // Micronewton (µN)
        0.001,              // Mili Newton (mN)
        1.0,                // Newton (N)
        1000.0,             // Quilonewton (kN)
        1_000_000.0,        // Meganewton (MN)
        1_000_000_000.0,    // Giganewton (GN)
        0.00001,            // Dina (dyn) - 1.0E-5
        1.0,                // Joule por metro (J/m) - é igual a Newton
        9.80665,            // Kilograma Força (kgf) - usando g padrão
        9806.65             // Tonelada-força (tnf) - 1000 * kgf
    )

    // Primeiro o input do usuário é convertido para N (Newtons)
    // Depois o resultado é convertido para a unidade de saída
    val valorEmN = variavel * unidadesEmN[itemPosition1]
    resultado = (valorEmN / unidadesEmN[itemPosition2]).toString()

    // Retorna o resultado formatado
    return formatarResultado(resultado)
}

// Conversão Ângulo
fun convertAngulo (inputUnidade: String,      // Input do usuário
                 itemPosition1: Int,        // Unidade de entrada (de)
                 itemPosition2: Int         // Unidade de saída (para)
): String {
    /*
    Lista de unidades disponíveis
    [0] -> Grau (°)
    [1] -> Radiano (rad)
    [2] -> Gradiano (grad)
    */

    // Resultado da conversão
    var resultado: String = "0"
    // Variável usada no cálculo. Tenta converter o input do usuário para um número
    // caso não consiga, retorna 0
    // Também substitui a vírgula por ponto para evitar erros de conversão
    val textoParaParse = inputUnidade.replace(',', '.')
    var variavel: Double = textoParaParse.toDoubleOrNull() ?: 0.0

    // Lista de unidades convertidas para Graus (°)
    val unidadesEmGraus = listOf(
        1.0,             // Grau (°)
        180.0 / PI,      // Radiano (rad) -> Graus (aprox. 57.2958)
        0.9              // Gradiano (grad) -> Graus (360/400)
    )

    // Primeiro o input do usuário é convertido para Graus (°)
    // Depois o resultado é convertido para a unidade de saída
    val valorEmGraus = variavel * unidadesEmGraus[itemPosition1]
    resultado = (valorEmGraus / unidadesEmGraus[itemPosition2]).toString()

    // Retorna o resultado formatado
    return formatarResultado(resultado)
}

// Conversão Frequência
fun convertFrequencia(inputUnidade: String,      // Input do usuário
                     itemPosition1: Int,        // Unidade de entrada (de)
                     itemPosition2: Int         // Unidade de saída (para)
): String {
    /*
    Lista de unidades disponíveis
    [0] -> Nanohertz (nHz)
    [1] -> Microhertz (µHz)
    [2] -> Milihertz (mHz)
    [3] -> Hertz (Hz)
    [4] -> Kilohertz (kHz)
    [5] -> Megahertz (MHz)
    [6] -> Gigahertz (GHz)
    [7] -> Terahertz (THz)
    [8] -> Ciclos por segundo (cps)
    [9] -> Rotações por minuto (rpm)
    [10] -> Batidas por minuto (BPM)
    [11] -> Radianos por segundo (rad/s)
    [12] -> Radianos por minuto
    [13] -> Radianos por hora
    [14] -> Radianos por dia
    */

    // Resultado da conversão
    var resultado: String = "0"
    // Variável usada no cálculo. Tenta converter o input do usuário para um número
    // caso não consiga, retorna 0
    // Também substitui a vírgula por ponto para evitar erros de conversão
    val textoParaParse = inputUnidade.replace(',', '.')
    var variavel: Double = textoParaParse.toDoubleOrNull() ?: 0.0

    // Lista de unidades convertidas para Hertz (Hz)
    val unidadesEmHz = listOf(
        1.0E-9,             // Nanohertz (nHz)
        1.0E-6,             // Microhertz (µHz)
        0.001,              // Milihertz (mHz)
        1.0,                // Hertz (Hz)
        1000.0,             // Kilohertz (kHz)
        1_000_000.0,        // Megahertz (MHz)
        1_000_000_000.0,    // Gigahertz (GHz)
        1_000_000_000_000.0,// Terahertz (THz)
        1.0,                // Ciclos por segundo (cps) - é igual a Hz
        1.0 / 60.0,         // Rotações por minuto (rpm) -> Hz (divide por 60)
        1.0 / 60.0,         // Batidas por minuto (BPM) -> Hz (divide por 60)
        1.0 / (2.0 * PI),   // Radianos por segundo (rad/s) -> Hz (divide por 2*PI)
        1.0 / (2.0 * PI * 60.0), // Radianos por minuto -> Hz
        1.0 / (2.0 * PI * 3600.0), // Radianos por hora -> Hz
        1.0 / (2.0 * PI * 86400.0) // Radianos por dia -> Hz
    )

    // Primeiro o input do usuário é convertido para Hz (Hertz)
    // Depois o resultado é convertido para a unidade de saída
    val valorEmHz = variavel * unidadesEmHz[itemPosition1]
    resultado = (valorEmHz / unidadesEmHz[itemPosition2]).toString()

    // Retorna o resultado formatado
    return formatarResultado(resultado)
}

// Conversão Torque
fun convertTorque(inputUnidade: String,      // Input do usuário
                  itemPosition1: Int,        // Unidade de entrada (de)
                  itemPosition2: Int         // Unidade de saída (para)
): String {
    /*
    Lista de unidades disponíveis
    [0] -> Newton-metro (N·m)
    [1] -> Quilonewton-metro (kN·m)
    [2] -> Milinewton-metro (mN·m)
    [3] -> Quilograma-força metro (kgf·m)
    [4] -> Quilograma-força centímetro (kgf·cm)
    [5] -> Grama-força centímetro (gf·cm)
    [6] -> Libra-força pé (lbf·ft)
    [7] -> Libra-força polegada (lbf·in)
    [8] -> Onça-força polegada (ozf·in)
    [9] -> Dina-centímetro (dyn·cm)
    */

    // Resultado da conversão
    var resultado: String = "0"
    // Variável usada no cálculo. Tenta converter o input do usuário para um número
    // caso não consiga, retorna 0
    // Também substitui a vírgula por ponto para evitar erros de conversão
    val textoParaParse = inputUnidade.replace(',', '.')
    var variavel: Double = textoParaParse.toDoubleOrNull() ?: 0.0

    // Lista de unidades convertidas para Newton-metro (N·m)
    // (Fator para kgf e gf usa g ≈ 9.80665 m/s²)
    val unidadesEmNm = listOf(
        1.0,                       // Newton-metro (N·m)
        1000.0,                    // Quilonewton-metro (kN·m)
        0.001,                     // Milinewton-metro (mN·m)
        9.80665,                   // Quilograma-força metro (kgf·m)
        0.0980665,                 // Quilograma-força centímetro (kgf·cm) -> Nm (9.80665 / 100)
        0.0000980665,              // Grama-força centímetro (gf·cm) -> Nm (0.0980665 / 1000) ou 9.80665E-5
        1.3558179483,              // Libra-força pé (lbf·ft) -> Nm
        0.112984829,               // Libra-força polegada (lbf·in) -> Nm (1.3558179 / 12)
        0.0070615518,              // Onça-força polegada (ozf·in) -> Nm (0.1129848 / 16)
        0.0000001                  // Dina-centímetro (dyn·cm) -> Nm (1E-7)
    )

    // Primeiro o input do usuário é convertido para N·m (Newton-metro)
    // Depois o resultado é convertido para a unidade de saída
    val valorEmNm = variavel * unidadesEmNm[itemPosition1]
    resultado = (valorEmNm / unidadesEmNm[itemPosition2]).toString()

    // Retorna o resultado formatado
    return formatarResultado(resultado)
}

// Conversão Capacitância
fun convertCapacitancia(inputUnidade: String,      // Input do usuário
                       itemPosition1: Int,        // Unidade de entrada (de)
                       itemPosition2: Int         // Unidade de saída (para)
): String {
    /*
    Lista de unidades disponíveis
    [0] -> Faraday (F)
    [1] -> Decafarad (daF)
    [2] -> Hectofarad (hF)
    [3] -> Quilofarad (kF)
    [4] -> Megafarad (MF)
    [5] -> Gigafarad (GF)
    [6] -> Terafarad (TF)
    [7] -> Decifarad (dF)
    [8] -> Centifarad (cF)
    [9] -> Milifarad (mF)
    [10] -> Microfarad (µF)
    [11] -> Nanofarad (nF)
    [12] -> PicoFarad (pF)
    [13] -> Coulomb por volt (C/V)
    [14] -> Abfarad (abF)
    */

    // Resultado da conversão
    var resultado: String = "0"
    // Variável usada no cálculo. Tenta converter o input do usuário para um número
    // caso não consiga, retorna 0
    // Também substitui a vírgula por ponto para evitar erros de conversão
    val textoParaParse = inputUnidade.replace(',', '.')
    var variavel: Double = textoParaParse.toDoubleOrNull() ?: 0.0

    // Lista de unidades convertidas para Farads (F)
    val unidadesEmF = listOf(
        1.0,                // Faraday (F)
        10.0,               // Decafarad (daF)
        100.0,              // Hectofarad (hF)
        1000.0,             // Quilofarad (kF)
        1.0E6,              // Megafarad (MF)
        1.0E9,              // Gigafarad (GF)
        1.0E12,             // Terafarad (TF)
        0.1,                // Decifarad (dF)
        0.01,               // Centifarad (cF)
        0.001,              // Milifarad (mF)
        1.0E-6,             // Microfarad (µF)
        1.0E-9,             // Nanofarad (nF)
        1.0E-12,            // PicoFarad (pF)
        1.0,                // Coulomb por volt (C/V) - é a definição de Farad
        1.0E9               // Abfarad (abF) - unidade CGS eletromagnética
    )

    // Primeiro o input do usuário é convertido para F (Farads)
    // Depois o resultado é convertido para a unidade de saída
    val valorEmF = variavel * unidadesEmF[itemPosition1]
    resultado = (valorEmF / unidadesEmF[itemPosition2]).toString()

    // Retorna o resultado formatado
    return formatarResultado(resultado)
}

// Conversão Carga Elétrica
fun convertCargaEletrica(inputUnidade: String,      // Input do usuário
                          itemPosition1: Int,        // Unidade de entrada (de)
                          itemPosition2: Int         // Unidade de saída (para)
): String {
    /*
    Lista de unidades disponíveis
    [0] -> Nanocoulomb (nC)
    [1] -> Microcoulomb (µC)
    [2] -> Milicoulomb (mC)
    [3] -> Coulomb (C)
    [4] -> Kilocoulomb (kC)
    [5] -> Megacoulomb (MC)
    [6] -> Abcoulomb (abC)
    [7] -> Miliampere-hora (mAh)
    [8] -> Ampere-hora (Ah)
    [9] -> Faraday (F)
    [10] -> Statcoulomb (statC)
    [11] -> Carga elementar (e)
    */

    // Resultado da conversão
    var resultado: String = "0"
    // Variável usada no cálculo. Tenta converter o input do usuário para um número
    // caso não consiga, retorna 0
    // Também substitui a vírgula por ponto para evitar erros de conversão
    val textoParaParse = inputUnidade.replace(',', '.')
    var variavel: Double = textoParaParse.toDoubleOrNull() ?: 0.0

    // Lista de unidades convertidas para Coulombs (C)
    // (Fatores exatos ou aproximados CODATA 2018 onde aplicável)
    val unidadesEmC = listOf(
        1.0E-9,                     // Nanocoulomb (nC)
        1.0E-6,                     // Microcoulomb (µC)
        0.001,                      // Milicoulomb (mC)
        1.0,                        // Coulomb (C)
        1000.0,                     // Kilocoulomb (kC)
        1.0E6,                      // Megacoulomb (MC)
        10.0,                       // Abcoulomb (abC) - unidade CGS emu
        3.6,                        // Miliampere-hora (mAh) -> C (0.001 A * 3600 s)
        3600.0,                     // Ampere-hora (Ah) -> C (1 A * 3600 s)
        96485.33212331001,          // Faraday (F) - constante, C/mol
        3.3356409519815205E-10,     // Statcoulomb (statC) ou Franklin (Fr) - unidade CGS esu
        1.602176634E-19             // Carga elementar (e)
    )

    // Primeiro o input do usuário é convertido para C (Coulombs)
    // Depois o resultado é convertido para a unidade de saída
    val valorEmC = variavel * unidadesEmC[itemPosition1]
    resultado = (valorEmC / unidadesEmC[itemPosition2]).toString()

    // Retorna o resultado formatado
    return formatarResultado(resultado)
}

// Conversão Corrente Elétrica
fun convertCorrenteEletrica(inputUnidade: String,      // Input do usuário
                           itemPosition1: Int,        // Unidade de entrada (de)
                           itemPosition2: Int         // Unidade de saída (para)
): String {
    /*
    Lista de unidades disponíveis
    [0] -> Nanoampère (nA)
    [1] -> Microampère (µA)
    [2] -> Miliampère (mA)
    [3] -> Ampère (A)
    [4] -> Kiloampère (kA)
    [5] -> Megaampère (MA)
    [6] -> Gigaampère (GA)
    [7] -> Abampère (aA)
    [8] -> Coulomb por segundo (C/s)
    */

    // Resultado da conversão
    var resultado: String = "0"
    // Variável usada no cálculo. Tenta converter o input do usuário para um número
    // caso não consiga, retorna 0
    // Também substitui a vírgula por ponto para evitar erros de conversão
    val textoParaParse = inputUnidade.replace(',', '.')
    var variavel: Double = textoParaParse.toDoubleOrNull() ?: 0.0

    // Lista de unidades convertidas para Ampères (A)
    val unidadesEmA = listOf(
        1.0E-9,             // Nanoampère (nA)
        1.0E-6,             // Microampère (µA)
        0.001,              // Miliampère (mA)
        1.0,                // Ampère (A)
        1000.0,             // Kiloampère (kA)
        1.0E6,              // Megaampère (MA)
        1.0E9,              // Gigaampère (GA)
        10.0,               // Abampère (aA) - unidade CGS emu
        1.0                 // Coulomb por segundo (C/s) - é a definição de Ampère
    )

    // Primeiro o input do usuário é convertido para A (Ampères)
    // Depois o resultado é convertido para a unidade de saída
    val valorEmA = variavel * unidadesEmA[itemPosition1]
    resultado = (valorEmA / unidadesEmA[itemPosition2]).toString()

    // Retorna o resultado formatado
    return formatarResultado(resultado)
}

// Conversão Resistência Elétrica
fun convertResistence(inputUnidade: String,      // Input do usuário
                      itemPosition1: Int,        // Unidade de entrada (de)
                      itemPosition2: Int         // Unidade de saída (para)
): String {
    /*
    Lista de unidades disponíveis
    [0] -> Nanoohm (nΩ)
    [1] -> Microohm (µΩ)
    [2] -> Miliohm (mΩ)
    [3] -> Ohm (Ω)
    [4] -> Kiloohm (kΩ)
    [5] -> Megaohm (MΩ)
    [6] -> Gigaohm (GΩ)
    [7] -> Abohm (abΩ)
    [8] -> Volt por ampère (V/A)
    */

    // Resultado da conversão
    var resultado: String = "0"
    // Variável usada no cálculo. Tenta converter o input do usuário para um número
    // caso não consiga, retorna 0
    // Também substitui a vírgula por ponto para evitar erros de conversão
    val textoParaParse = inputUnidade.replace(',', '.')
    var variavel: Double = textoParaParse.toDoubleOrNull() ?: 0.0

    // Lista de unidades convertidas para Ohms (Ω)
    val unidadesEmOhm = listOf(
        1.0E-9,             // Nanoohm (nΩ)
        1.0E-6,             // Microohm (µΩ)
        0.001,              // Miliohm (mΩ)
        1.0,                // Ohm (Ω)
        1000.0,             // Kiloohm (kΩ)
        1.0E6,              // Megaohm (MΩ)
        1.0E9,              // Gigaohm (GΩ)
        1.0E-9,             // Abohm (abΩ) - unidade CGS emu
        1.0                 // Volt por ampère (V/A) - é a definição de Ohm
    )

    // Primeiro o input do usuário é convertido para Ω (Ohms)
    // Depois o resultado é convertido para a unidade de saída
    val valorEmOhm = variavel * unidadesEmOhm[itemPosition1]
    resultado = (valorEmOhm / unidadesEmOhm[itemPosition2]).toString()

    // Retorna o resultado formatado
    return formatarResultado(resultado)
}

// Conversão Tensão Elétrica (Voltagem)
fun convertTensao(inputUnidade: String,      // Input do usuário
                   itemPosition1: Int,        // Unidade de entrada (de)
                   itemPosition2: Int         // Unidade de saída (para)
): String {
    /*
    Lista de unidades disponíveis
    [0] -> Nanovolt (nV)
    [1] -> Microvolt (µV)
    [2] -> Millivolt (mV)
    [3] -> Volt (V)
    [4] -> Kilovolt (kV)
    [5] -> Megavolt (MV)
    [6] -> Gigavolt (GV)
    [7] -> Watt por ampère (W/A)
    [8] -> Abvolt (abV)
    [9] -> Statvolt (stV)
    */

    // Resultado da conversão
    var resultado: String = "0"
    // Variável usada no cálculo. Tenta converter o input do usuário para um número
    // caso não consiga, retorna 0
    // Também substitui a vírgula por ponto para evitar erros de conversão
    val textoParaParse = inputUnidade.replace(',', '.')
    var variavel: Double = textoParaParse.toDoubleOrNull() ?: 0.0

    // Lista de unidades convertidas para Volts (V)
    val unidadesEmV = listOf(
        1.0E-9,             // Nanovolt (nV)
        1.0E-6,             // Microvolt (µV)
        0.001,              // Millivolt (mV)
        1.0,                // Volt (V)
        1000.0,             // Kilovolt (kV)
        1.0E6,              // Megavolt (MV)
        1.0E9,              // Gigavolt (GV)
        1.0,                // Watt por ampère (W/A) - é igual a Volt (V = P/I)
        1.0E-8,             // Abvolt (abV) - unidade CGS emu
        299.792458          // Statvolt (stV) - unidade CGS esu (c/10^8, onde c é a vel. da luz em m/s)
    )

    // Primeiro o input do usuário é convertido para V (Volts)
    // Depois o resultado é convertido para a unidade de saída
    val valorEmV = variavel * unidadesEmV[itemPosition1]
    resultado = (valorEmV / unidadesEmV[itemPosition2]).toString()

    // Retorna o resultado formatado
    return formatarResultado(resultado)
}

// Conversão Densidade
fun convertDensidade(inputUnidade: String,      // Input do usuário
                   itemPosition1: Int,        // Unidade de entrada (de)
                   itemPosition2: Int         // Unidade de saída (para)
): String {
    /*
    Lista de unidades disponíveis
    [0] -> Grama por Centímetro Cúbico (g/cm³)
    [1] -> Quilograma por metro cúbico (kg/m³)
    [2] -> Grama por Metro Cúbico (g/m³)
    [3] -> Miligrama por Metro Cúbico (mg/m³)
    [4] -> Onça por Galão (oz/gal)
    [5] -> Libra por pé cúbico (lb/ft³)
    [6] -> Libra por polegada cúbica (lb/in³)
    */

    // Resultado da conversão
    var resultado: String = "0"
    // Variável usada no cálculo. Tenta converter o input do usuário para um número
    // caso não consiga, retorna 0
    // Também substitui a vírgula por ponto para evitar erros de conversão
    val textoParaParse = inputUnidade.replace(',', '.')
    var variavel: Double = textoParaParse.toDoubleOrNull() ?: 0.0

    // Lista de unidades convertidas para Quilograma por metro cúbico (kg/m³)
    // (Fatores aproximados para unidades imperiais/US)
    val unidadesEmKgM3 = listOf(
        1000.0,             // Grama por Centímetro Cúbico (g/cm³) -> kg/m³
        1.0,                // Quilograma por metro cúbico (kg/m³)
        0.001,              // Grama por Metro Cúbico (g/m³) -> kg/m³
        1.0E-6,             // Miligrama por Metro Cúbico (mg/m³) -> kg/m³
        7.4891517,          // Onça por Galão (oz/gal) -> kg/m³ (usando galão líquido US)
        16.018463,          // Libra por pé cúbico (lb/ft³) -> kg/m³
        27679.905           // Libra por polegada cúbica (lb/in³) -> kg/m³
    )

    // Primeiro o input do usuário é convertido para kg/m³
    // Depois o resultado é convertido para a unidade de saída
    val valorEmKgM3 = variavel * unidadesEmKgM3[itemPosition1]
    resultado = (valorEmKgM3 / unidadesEmKgM3[itemPosition2]).toString()

    // Retorna o resultado formatado
    return formatarResultado(resultado)
}