package com.example.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unitconverter.ui.theme.UnitConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitConverterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UnitConverter(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

enum class Unit {
    Centimeters, Meters, Feet, Millimeters;

    fun conversionFactor() = when (this) {
        Centimeters -> 0.01
        Meters -> 1.0
        Feet -> 0.3048
        Millimeters -> 0.001
    }
}

data class UnitConverterState(
    val inputValue: String,
    val inputUnit: Unit,
    val outputUnit: Unit
) {

    val outputValue: String
        get() {
            val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
            val result =
                (inputValueDouble * inputUnit.conversionFactor() * 100
                        / outputUnit.conversionFactor()).toInt() / 100.0
            return result.toString()
        }
}

@Composable
fun UnitConverter(modifier: Modifier = Modifier) {
    var state by remember {
        mutableStateOf(
            UnitConverterState("", Unit.Meters, Unit.Meters)
        )
    }
    var inputUnitsExpanded by remember { mutableStateOf(false) }
    var outputUnitsExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Unit Converter", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.inputValue,
            onValueChange = {
                state = state.copy(inputValue = it)
            },
            label = { Text("Enter Value") },
            modifier = Modifier.testTag("inputValueTextField")
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            // Input Box
            Box {
                Button(
                    onClick = { inputUnitsExpanded = true },
                    modifier = Modifier.testTag("inputUnitButton")
                ) {
                    Text(state.inputUnit.name)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
                }
                DropdownMenu(
                    expanded = inputUnitsExpanded,
                    onDismissRequest = { inputUnitsExpanded = false }) {
                    for (unit in Unit.entries) {
                        DropdownMenuItem(text = { Text(unit.name) }, onClick = {
                            state = state.copy(inputUnit = unit)
                            inputUnitsExpanded = false
                        })
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            // Output Box
            Box {
                Button(
                    onClick = { outputUnitsExpanded = true },
                    modifier = Modifier.testTag("outputUnitButton")
                ) {
                    Text(state.outputUnit.name)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
                }
                DropdownMenu(
                    expanded = outputUnitsExpanded,
                    onDismissRequest = { outputUnitsExpanded = false }) {
                    for (unit in Unit.entries) {
                        DropdownMenuItem(text = { Text(unit.name) }, onClick = {
                            state = state.copy(outputUnit = unit)
                            outputUnitsExpanded = false
                        })
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Result: ${state.outputValue} ${state.outputUnit}",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}


@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    UnitConverterTheme {
        UnitConverter()
    }
}