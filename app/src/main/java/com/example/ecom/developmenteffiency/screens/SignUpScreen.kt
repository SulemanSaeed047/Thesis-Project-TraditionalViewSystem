package com.example.ecom.developmenteffiency.screens

import android.util.Patterns
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecom.developmenteffiency.ViewModels.DEViewModel
import com.example.ecom.R
import com.example.ecom.developmenteffiency.API.ModelClasses.SignUpUser
import com.example.ecom.ui.theme.EcomTheme


@Composable
fun SignUpScreen(viewmodel: DEViewModel, moveToLogin:()->Unit,) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    var isSignUp = viewmodel.isSignUp.observeAsState()

    val context = LocalContext.current

    LaunchedEffect(isSignUp.value) {
        if (isSignUp.value == true){
            moveToLogin()
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
            text = "SIgn Up",
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
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
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
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (username.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        Toast.makeText(context, "Invalid email format", Toast.LENGTH_SHORT).show()
                    } else {
                        viewmodel.SignUpUser(context,SignUpUser(username = username, email = email, password = password))
                    }
                } else {
                    Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
                }

        }) {
            Text("SIgn Up")
        }
        Spacer(modifier = Modifier.height(50.dp))
        Divider()
        Spacer(modifier = Modifier.height(8.dp))
        Text("Log In?", color = Color.Gray, modifier = Modifier.clickable {
            moveToLogin()
        })
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignUpPreview() {
    EcomTheme {
       // SignUpScreen()
    }
}

