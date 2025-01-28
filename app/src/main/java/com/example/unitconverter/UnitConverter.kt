package com.example.unitconverter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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

data class UnitConverterState(
    val inputValue: String,
    val inputUnit: Unit,
    val outputUnit: Unit
) {

    val outputValue: String
        get() {
            val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
            val result =
                (inputValueDouble * inputUnit.conversionFactor * 100
                        / outputUnit.conversionFactor).toInt() / 100.0
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
            UnitDropdown(
                state.inputUnit,
                { state = state.copy(inputUnit = it) },
                testTag = "inputUnitButton"
            )
            Spacer(modifier = Modifier.width(16.dp))
            // Output Box
            UnitDropdown(
                state.outputUnit,
                { state = state.copy(outputUnit = it) },
                testTag = "outputUnitButton"
            )
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