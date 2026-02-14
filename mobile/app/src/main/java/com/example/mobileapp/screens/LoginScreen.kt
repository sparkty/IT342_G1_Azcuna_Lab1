package com.example.mobileapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobileapp.model.User
import com.example.mobileapp.network.ApiService
import kotlinx.coroutines.launch
import retrofit2.Response

@Composable
fun LoginScreen(navController: NavHostController) {
    val api = ApiService.create()
    val scope = rememberCoroutineScope()
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.Center) {
        TextField(value = username, onValueChange = { username = it }, label = { Text("Username") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = password, onValueChange = { password = it }, label = { Text("Password") })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            scope.launch {
                try {
                    val response = api.login(User(username, "", password))
                    message = response.body() ?: "Login failed"
                    if (message.contains("successful", ignoreCase = true)) {
                        navController.navigate("dashboard") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                } catch (e: Exception) {
                    message = "Error: ${e.localizedMessage}"
                }
            }
        }) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = { navController.navigate("register") }) {
            Text("Go to Register")
        }
        if (message.isNotEmpty()) Text(message, color = MaterialTheme.colorScheme.error)
    }
}