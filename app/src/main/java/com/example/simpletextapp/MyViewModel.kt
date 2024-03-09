package com.example.simpletextapp

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import kotlinx.coroutines.CancellationException

// https://developer.android.com/topic/libraries/architecture/viewmodel
class MyViewModel: ViewModel() {

    val matNoState = mutableStateOf("")

    // pattern: see android documentation & tutorials -->
    // the private _matNoState provides a mutable container for holding the internal state data
    private val _responseState = mutableStateOf("")
    // the public textState acts as a read-only copy for the UI to observe the state and trigger recompositions
    val responseState: State<String> = _responseState
    fun fetchData(){
        val serverAddress = "se2-submission.aau.at" // Replace with your server's IP address
        val port = 20080 // Replace with your server's port number

        // use IO dispatcher to start new coroutine on background thread
        viewModelScope.launch(Dispatchers.IO) {// launch coroutine for communication with server
            val job = coroutineContext.job  // retrieve current job (coroutine task?)

            Log.d("fetchData()", "matNoState: ${matNoState.value}")

            // here I wanted to try using kotlin's "use" function.
            // it automatically deals with the basic resource management of closable resources.
            try {
                Socket(serverAddress, port).use { socket ->
                    PrintWriter(socket.getOutputStream(), true).use { outputStream ->
                        BufferedReader(InputStreamReader(socket.getInputStream())).use { inputStream ->
                            // write data to server via outputStream:
                            //val messageToSend = "11702848"  // for testing
                            //outputStream.println(messageToSend)
                            outputStream.println(matNoState.value)

                            // read response from the server
                            val response = inputStream.readLine()
                            _responseState.value = response
                            //println("Server response: $response")
                            Log.d("fetchData()", "Server response: ${responseState.value}")
                        }
                    }
                }
            } catch (e: Exception) {
                if (e !is CancellationException) {
                    Log.e("fetchData()", "Error while connecting to Server: ${e.message}")
                }
            } finally {
                if (job.isCancelled) {
                    // if coroutine was cancelled
                    Log.e("fetchData()", "Coroutine was cancelled")
                }
            }
        }
    }
}