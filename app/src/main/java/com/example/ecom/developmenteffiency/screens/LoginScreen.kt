package com.example.ecom.developmenteffiency.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecom.developmenteffiency.ViewModels.DEViewModel
import com.example.ecom.R
import com.example.ecom.developmenteffiency.API.ModelClasses.LoginReq

@Composable
fun LoginScreen(
    viewmodel: DEViewModel,
    moveToSignUp:()->Unit,
    moveToHome:()->Unit,
    ) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    var isLogin = viewmodel.isLogin.observeAsState()

    LaunchedEffect(isLogin.value) {
        if (isLogin.value == true){
            Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
            moveToHome()
        }
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(70.dp))
        Text(
            text = "Log In",
            fontSize = 25.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontFamily = FontFamily(Font(R.raw.iceberg_regular))
        )
        Spacer(modifier = Modifier.height(100.dp))
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(15.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    viewmodel.loginUser(context, LoginReq(username,password))
                } else {
                    Toast.makeText(context, "Please enter all fields", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(50.dp))
        Divider()
        Spacer(modifier = Modifier.height(8.dp))
        Text("Sign Up?", color = Color.Gray, modifier = Modifier.clickable {
            moveToSignUp()
        })
    }
}