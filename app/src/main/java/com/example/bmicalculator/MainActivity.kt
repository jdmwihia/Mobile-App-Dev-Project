package com.example.bmicalculator

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
import com.example.bmicalculator.ui.theme.BMICalculatorTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.material3.Button
import androidx.compose.runtime.MutableState



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BMICalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val heightState = rememberSaveable { mutableStateOf("") }
                    val weightState = rememberSaveable { mutableStateOf("") }
                    val bmiResult = remember { mutableStateOf<Float?>(null) }
                    Column(modifier = Modifier.padding(innerPadding)) {
                        Header()
                        Height(heightState = heightState)
                        Weight(weightState = weightState)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CalcBMIButton(
                                heightState = heightState,
                                weightState = weightState,
                                bmiResult = bmiResult
                            )
                            DisplayBMI(bmiResult = bmiResult)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun Header(modifier: Modifier = Modifier){
    Text(
        text = "BMI Calculator",
        color = Color.White,
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Blue)
            .padding(20.dp)
    )
}

@Composable
fun Height(
    heightState: MutableState<String>,
    modifier: Modifier = Modifier){
    Row(modifier = modifier.padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,) {
        Text(
            text = "Height",
            modifier = Modifier
                .padding(end = 9.dp)
        )

        OutlinedTextField(
            value = heightState.value,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) {
                    heightState.value = newValue
                }
            },
            singleLine = true,
            modifier = Modifier
                .width(100.dp)
        )

        Text(
            text = "cm",
            modifier = Modifier
                .padding(start = 9.dp)
        )
    }
}

@Composable
fun Weight(
    weightState: MutableState<String>,
    modifier: Modifier = Modifier){
    Row(modifier = modifier.padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,) {
        Text(
            text = "Weight",
            modifier = Modifier
                .padding(end = 9.dp)
        )

        OutlinedTextField(
            value = weightState.value,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) {
                    weightState.value = newValue
                }
            },
            singleLine = true,
            modifier = Modifier
                .width(100.dp)
        )

        Text(
            text = "kg",
            modifier = Modifier
                .padding(start = 9.dp)
        )
    }
}

@Composable
fun CalcBMIButton(
    heightState: MutableState<String>,
    weightState: MutableState<String>,
    bmiResult: MutableState<Float?>
){
    Button(
        onClick = {
            val heightCm = heightState.value.toFloatOrNull()
            val weightKg = weightState.value.toFloatOrNull()

            if(heightCm != null && weightKg !=null ){
                val heightM = heightCm / 100f
                val bmi = weightKg / (heightM * heightM)
                bmiResult.value = bmi
            }else{
                bmiResult.value = null

            }
        },
        modifier = Modifier.padding(20.dp)
    ){
        Text("Calculate BMI")
    }

}

@Composable
fun DisplayBMI(
    bmiResult: MutableState<Float?>,
    modifier: Modifier = Modifier
) {
    bmiResult.value?.let { bmi ->
        val message = when {
            bmi < 18.5 -> "Underweight"
            bmi < 25 -> "Normal weight"
            bmi < 30 -> "Overweight"
            else -> "Obese"
        }
        Text(
            text = "Your BMI is %.2f. You are $message".format(bmi),
            modifier = modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BMICalculatorTheme {
        val previewHeightState = rememberSaveable { mutableStateOf("")}
        val previewWeightState = rememberSaveable { mutableStateOf("") }
        val previewBMIState = rememberSaveable { mutableStateOf<Float?>(null) }
        Column {
            Header()
            Height(heightState = previewHeightState)
            Weight(weightState = previewWeightState)
            CalcBMIButton(
                heightState = previewHeightState,
                weightState = previewWeightState,
                bmiResult = previewBMIState
            )
            Row {}
        }
    }
}