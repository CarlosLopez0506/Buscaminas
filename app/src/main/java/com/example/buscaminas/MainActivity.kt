package com.example.buscaminas

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.buscaminas.ui.theme.BuscaminasTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BuscaminasTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        titulo()
                        buscaMinas("Android")
                    }
                }
            }
        }
    }
}

@Composable
fun buscaMinas(name: String, modifier: Modifier = Modifier) {

    val columnas = 6
    val filas = 10

    val estadoBotones = remember {
        List(filas * columnas) { mutableStateOf(true) }
    }

    val minas = remember {
        List(filas * columnas) { mutableStateOf(asignaMinas()) }
    }

    val varAlerta = remember {
        mutableStateOf(false)
    }

    if (varAlerta.value) {
        AlertDialog(
            onDismissRequest = { varAlerta.value = false },
            confirmButton = {
                Button(onClick = {
                    varAlerta.value = false

                    estadoBotones.forEach { it.value = true }
                    minas.forEach { it.value = asignaMinas() }
                }) {
                    Text(text = "Otra partida")
                }
                Button(onClick = { varAlerta.value = false }) {
                    Text(text = "Continuar")
                }
            },
            text = { Text(text = "Has perdido") },
            title = { Text(text = "Presione continuar para una nueva partida") })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        for (i in 0 until filas) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(.1f)
            ) {
                for (j in 0 until columnas) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(.1f)
                    ) {
                        val index = i * columnas + j
                        Button(
                            onClick = {
                                estadoBotones[index].value = false
                                if (minas[index].value) {
                                    varAlerta.value = true
                                }
                            },
                            modifier = Modifier.fillMaxSize(),
                            enabled = estadoBotones[index].value
                        ) {
                            Text(text = "0")
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun titulo() {
    Text(
        text = "Buena suerte, trata de no explotar",
        modifier = Modifier.fillMaxWidth(),
        fontSize = 20.sp,
        textAlign = TextAlign.Center
    )
}

fun asignaMinas(): Boolean {
    val random = java.util.Random()
    val number = random.nextInt(11)

    return number > 8
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BuscaminasTheme {
        Column {
            titulo()
            buscaMinas("Android")
        }
    }
}