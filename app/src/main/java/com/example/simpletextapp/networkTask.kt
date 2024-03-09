// This file is not in use, old approach that didn't work.
// Working approach for network task: MyViewModel.kt


package com.example.simpletextapp

import android.util.Log
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

fun fetchData(coroutineScope: CoroutineScope){
    val serverAddress = "se2-submission.aau.at" // Replace with your server's IP address
    val port = 20080 // Replace with your server's port number

    // use IO dispatcher to start new coroutine on background thread
    coroutineScope.launch(Dispatchers.IO) {// launch coroutine for communication with server
        val job = coroutineContext.job  // retrieve current job (coroutine task?)

        // here I wanted to try using kotlin's "use" function.
        // it automatically deals with the basic resource management of closable resources.
        try {
            Socket(serverAddress, port).use { socket ->
                PrintWriter(socket.getOutputStream(), true).use { outputStream ->
                    BufferedReader(InputStreamReader(socket.getInputStream())).use { inputStream ->
                        // write data to server via outputStream:
                        val messageToSend = "11702848"
                        outputStream.println(messageToSend)
                        // outputStream.write(messageToSend)

                        // read response from the server
                        val response = inputStream.readLine()
                        //println("Server response: $response")
                        Log.d("fetchData()", "Server response: $response")
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
