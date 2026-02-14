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
fun DashboardScreen(navController: NavHostController) {
    val api = ApiService.create()
    val scope = rememberCoroutineScope()
    var user by remember { mutableStateOf<User?>(null) }
    var message by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val response = api.getUser()
                user = response.body()
                if (!response.isSuccessful) message = "Failed to load user"
            } catch (e: Exception) {
                message = "Error: ${e.localizedMessage}"
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.Center) {
        Text("Dashboard/Profile", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        user?.let {
            Text("Username: ${it.username}")
            Text("Email: ${it.email}")
        } ?: Text(message.ifEmpty { "Loading user info..." })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            navController.navigate("login") { popUpTo("dashboard") { inclusive = true } }
        }) {
            Text("Logout")
        }
    }
}