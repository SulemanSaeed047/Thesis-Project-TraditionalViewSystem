package com.example.ecom.developmenteffiency.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecom.developmenteffiency.ViewModels.DEViewModel
import com.example.ecom.R
import com.example.ecom.developmenteffiency.API.ModelClasses.SignUpUser
import com.example.ecom.ui.theme.EcomTheme

@Composable
fun AllUsersScreen(viewModel: DEViewModel, onPrevClick:(Long)->Unit){

    LaunchedEffect(true) {
        viewModel.getAllUsers()
    }

    val allUser = viewModel.allUsers.observeAsState()
    LazyColumn(
        modifier = Modifier.padding(12.dp).fillMaxSize()
    ) {
        item {
            Text(text = "All Users", fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurfaceVariant )
            Spacer(modifier = Modifier.height(15.dp))
        }
        if (allUser.value.isNullOrEmpty()){
            item{
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.width(64.dp).align(Alignment.Center)
                            .align(Alignment.Center)
                    )
                }
            }
        }else{
            items(allUser.value ?: emptyList()){
                UserCard(it){userId->
                    onPrevClick(userId)
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
        }

    }
}

@Composable
fun UserCard(allUser: SignUpUser, onClick:(Long)->Unit) {
    Card ( elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        colors =  CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant )){
        Row( modifier = Modifier.padding(7.dp).clickable { onClick(allUser.id)}
        ) {
            Image(
                painterResource(R.drawable.user_profile),null,
                modifier = Modifier.size(70.dp).weight(2f))
            Column(modifier = Modifier.weight(8f).padding(start = 8.dp)) {
                Text(text = "${allUser.username}", fontSize = 14.sp, fontWeight = FontWeight.Bold,lineHeight = 18.sp,maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 12.dp))
                Text(text = "${allUser.email}", fontSize = 12.sp, maxLines = 3, lineHeight = 18.sp, overflow = TextOverflow.Ellipsis )
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AllUsersScreenPre() {
    EcomTheme {
        LazyColumn {
            items(12){
               // UserCard({})
            }
        }
    }
}