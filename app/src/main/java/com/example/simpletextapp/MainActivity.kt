package com.example.simpletextapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simpletextapp.ui.theme.SimpleTextAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TextApp(modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun TextApp(modifier: Modifier = Modifier) {
    val textState = remember {
        mutableStateOf("")
    }
    val submittedText = remember {
        mutableStateOf("")
    }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
                UserInput(textState = textState)

/*                SubmitButton(onClick = {
                        submittedText.value = textState.value
                        textState.value = "" },
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                )*/
                SubmitButton(
                    textState, submittedText, onSubmit = ::handleSubmission,
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
                // :: is the reference operator. we use it to pass the reference of handleSubmission to the onSubmit parameter

                if (submittedText.value.isNotEmpty()){
                    SubmittedText(text = submittedText.value)
                }
            }
        }
    }
}

@Composable
fun UserInput(textState: MutableState<String>, modifier: Modifier = Modifier){
    TextField(
        value = textState.value,
        onValueChange = {textState.value = it},
        label = {Text("Enter some text")},
        modifier = modifier
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
}

@Composable
fun SubmitButton(
    textState: MutableState<String>,
    submittedText: MutableState<String>,
    onSubmit: (MutableState<String>, MutableState<String>) -> Unit,
    modifier: Modifier = Modifier)
{
    Button(onClick = {onSubmit(textState, submittedText)}, modifier = modifier) {
        Text("Submit")
    }
}

/**
 * called when the submit button is clicked.
 * writes textState content to submittedText
 */
fun handleSubmission(textState: MutableState<String>, submittedText: MutableState<String>) {
    submittedText.value = textState.value
    textState.value = ""
}

@Composable
fun SubmittedText(text: String){
    Text(text = text)
}

@Preview(
    showBackground = true,
    showSystemUi = true)
@Composable
fun AppPreview() {
    TextApp()
}