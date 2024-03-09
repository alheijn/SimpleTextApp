package com.example.simpletextapp

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

// viewModelScope
// https://medium.com/androiddevelopers/easy-coroutines-in-android-viewmodelscope-25bffb605471
class NetworkViewModel: ViewModel() {
    /**
     * runs asynchronously in a coroutine launched in viewModelScope.
     * connects and writes to server and reads the server response.
     */
    fun fetchDataFromServer(matNo: String = "11702848", responseState: MutableState<String>) {
        println("fetchDataFromServer()")
        Log.i("fetchDataFromServer()", "call with matNo: $matNo")
        viewModelScope.launch(Dispatchers.IO) {
            // use Dispatchers.IO to perform the network operation in a background thread

            val serverAddress = "se2-submission.aau.at"
            val port = 20080

            try {
                // open a socket connection to server
                Socket(serverAddress, port).use { socket ->
                    // get input stream and output stream from the socket
                    PrintWriter(socket.getOutputStream()).use { writer ->
                        Log.i("fetchDataFromServer()", "PrintWriter closure")
                        // send single-line request (Matrikelnummer) to server
                        writer.println(matNo)

                        BufferedReader(InputStreamReader(socket.getInputStream())).use { reader ->
                            Log.i("fetchDataFromServer()", "BufferedReader closure")

                            // read (single-line) response from server
                            val response = reader.readLine()
                            println("Server response: $response")
                            Log.i("fetchDataFromServer()", "server response: $response")
                            responseState.value = response
                            socket.close()
                        }
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}