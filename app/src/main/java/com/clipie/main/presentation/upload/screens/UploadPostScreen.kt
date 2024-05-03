package com.clipie.main.presentation.upload.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.clipie.main.presentation.upload.components.BackHomeButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadPostScreen(navController: NavController) {
    TopAppBar(title = {
        Text(text = "New Post", fontWeight = FontWeight.Bold)
    },
        navigationIcon = { BackHomeButton(navController = navController) },
        actions = {
            TextButton(onClick = { navController.navigate("") }) {
                Text(text = "Next", modifier = Modifier, fontSize = 25.sp)
            }
        })
}