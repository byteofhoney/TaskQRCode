package com.task.taskqrcode

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text("Certificate QR Code System")

        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { navController.navigate("generate") }) {
            Text("Certificate + QR Code")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { navController.navigate("scan") }) {
            Text("Scan QR Code")
        }
    }
}

