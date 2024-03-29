package com.example.simpletextapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TextApp(modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun TextApp(modifier: Modifier = Modifier, viewModel: MyViewModel = viewModel()) {
    val matNoState = remember {
        mutableStateOf("")
    }
    val submittedText = remember {
        mutableStateOf("")
    }

    val calculatedValueState = remember {
        mutableStateOf("")
    }

    // old, didn't work:
    /*val serverResponseState = remember {
        mutableStateOf("")
    }*/
    val serverResponseState = viewModel.responseState


    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Spacer(
                    modifier = Modifier
                        .height(40.dp)
                )

                UserInputTextField(
                    matNoState = matNoState,
                    modifier = Modifier
                )

                /*SubmitButton(onClick = {
                        submittedText.value = textState.value
                        textState.value = "" },
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                )*/
                Row(modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(top = 16.dp)
                ) {
                    SubmitAndCalcButton(
                        modifier = Modifier,
                        matNoState = matNoState,
                        submittedText = submittedText,
                        calculatedValueState = calculatedValueState,
                        onSubmit = ::handleSubmission
                    )
                    // :: is the reference operator. we use it to pass the reference of handleSubmission to the onSubmit parameter
                    // I (probably?) wouldn't need it if I used & passed a lambda instead... but nice to see how it works
                    CalculateButton(
                        matNoState = matNoState,
                        calculatedValueState = calculatedValueState
                    )

                }

                /*if (submittedText.value.isNotEmpty()){
                    SubmittedText(text = submittedText.value, modifier = Modifier.align(Alignment.CenterHorizontally))
                }*/
                SubmittedValueText(
                    text = submittedText.value,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                CalculatedValueDisplay(
                    calculatedValueState = calculatedValueState,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                ServerResponseText(
                    serverResponseState = serverResponseState
                )

            }
        }
    }
}

@Composable
fun UserInputTextField(matNoState: MutableState<String>, modifier: Modifier = Modifier){
    TextField(
        value = matNoState.value,
        onValueChange = {matNoState.value = it},
        label = {Text("Enter Matrikelnummer")},
        modifier = modifier
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
}

@Composable
fun SubmitAndCalcButton(
    modifier: Modifier = Modifier,
    matNoState: MutableState<String>,
    submittedText: MutableState<String>,
    calculatedValueState: MutableState<String>,
    //serverResponseState: MutableState<String>,
    onSubmit: (MutableState<String>, MutableState<String>, MutableState<String>) -> Unit,
    viewModel: MyViewModel = viewModel())
{
    //val viewModel: MyViewModel = viewModel()    // viewModel for network-communication coroutine scope

    Button(
        onClick = {
            onSubmit(matNoState, submittedText, calculatedValueState)
            Log.d("SubmitAndCalcButton onClick()", "submittedText.value = ${submittedText.value}")
            viewModel.matNoState.value = submittedText.value
            viewModel.fetchData()   // "update" viewModel
            //serverResponseState.value = viewModel.responseState.value   // get new data from viewModel
            //Log.d("SubmitAndCalcButton onClick()", "serverResponseState.value = ${serverResponseState.value}")
                  },
        modifier = Modifier
            .padding(end = 16.dp)
            .height(60.dp)) {
        Text("Submit +\nCalculate", textAlign = TextAlign.Center)
    }
}

@Composable
fun ServerResponseText(
    serverResponseState: State<String>,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(16.dp)
        .background(Color.LightGray) ) {
        Text(
            text = "Server Response: ",
            //fontSize = 10.sp,
            modifier = modifier.padding(16.dp))
        Text(
            text = serverResponseState.value,
            //fontSize = 10.sp,
            modifier = modifier.padding(16.dp)
        )
    }
}

/**
 * called when the submit button is clicked.
 * writes textState content to submittedText
 */
fun handleSubmission(
    matNoState: MutableState<String>,
    submittedText: MutableState<String>,
    calculatedValueState: MutableState<String>
) {
    submittedText.value = matNoState.value
    matNoState.value = ""
    // calculate the value of the submitted value (see calculateTask.kt):
    calculatedValueState.value = sortDigits(submittedText.value)
}

@Composable
fun SubmittedValueText(
    text: String,
    modifier: Modifier = Modifier
){
    Row(modifier = modifier
        .fillMaxWidth()
        .padding(16.dp)
        .background(Color.Green)) {
        Text(
            text = "Submitted value:",
            modifier = modifier.padding(16.dp))
        Text(
            text = text,
            modifier = modifier.padding(16.dp)
        )
    }
}

@Composable
fun CalculateButton(
    matNoState: MutableState<String>,
    calculatedValueState: MutableState<String>,
    modifier: Modifier = Modifier
){
    Button(
        onClick = {
            calculatedValueState.value = sortDigits(matNoState.value)
            //matNoState.value = ""
                  },
        modifier = Modifier
            .padding(end = 16.dp)
            .height(60.dp)) {
        Text("Calculate\nOnly", textAlign = TextAlign.Center)
    }
}

@Composable
fun CalculatedValueDisplay(
    calculatedValueState: MutableState<String>,
    modifier: Modifier = Modifier
){

    Row(modifier = modifier
        .fillMaxWidth()
        .padding(16.dp)
        .background(Color.Cyan) ) {
        Text(
            text = "Calculated value: ",
            modifier = modifier.padding(16.dp))
        Text(
            text = calculatedValueState.value,
            modifier = modifier.padding(16.dp)
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true)
@Composable
fun AppPreview() {
    TextApp()
}