package com.example.ecom.developmenteffiency.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecom.developmenteffiency.ViewModels.DEViewModel
import com.example.ecom.developmenteffiency.API.ModelClasses.SignUpUser
import com.example.ecom.ui.theme.EcomTheme


@Composable
fun UserDetailScreen(userId:Long?,viewModel: DEViewModel) {
    var userList = viewModel.allUsers.observeAsState(emptyList())
    val selectedUser = userList.value.find { it.id == userId?.toLong() }
    if (selectedUser != null) {
        LazyColumn(modifier = Modifier.fillMaxSize().padding(12.dp)) {
            item {
                Text(text = "User Details", fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(7.dp))
                Divider()
                Spacer(modifier = Modifier.height(15.dp))
            }
            item {
                UserDetail(selectedUser)
            }
        }
    } else {
        Text(text = "No user found", color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
fun UserDetail(user: SignUpUser) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DetailItem("First Name", user.name.firstname)
        DetailItem("Last Name", user.name.lastname)
        DetailItem("Email", user.email)
        DetailItem("Username", user.username)
        DetailItem("Phone", user.phone)
        DetailItem("City", user.address.city)
        DetailItem("Street", user.address.street)
        DetailItem("Number", user.address.number.toString())
        DetailItem("Zipcode", user.address.zipcode)
        DetailItem("Latitude", user.address.geolocation.lat)
        DetailItem("Longitude", user.address.geolocation.long)
    }
}

@Composable
fun DetailItem(field: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$field",
            color = Color.LightGray,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 16.sp,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AllUsersScreenPre1() {
    EcomTheme {
    }
}