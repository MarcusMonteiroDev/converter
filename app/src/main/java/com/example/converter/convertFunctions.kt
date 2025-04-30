package com.example.converter

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