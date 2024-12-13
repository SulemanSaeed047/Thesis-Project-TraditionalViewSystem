package com.example.ecom.developmenteffiency.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecom.R
import com.example.ecom.developmenteffiency.Routes.Routes
import com.example.ecom.ui.theme.EcomTheme

@Composable
fun MainScreen(
    moveTo:(String)->Unit,
    ) {

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val data = listOf<Pair<String,Int>>(
            Pair("Products",R.drawable.products),
            Pair("Users",R.drawable.users),
            Pair("Theme",R.drawable.theme),
            Pair("Setting",R.drawable.settings),
        )
        Spacer(modifier = Modifier.height(20.dp))
        Image(painterResource(R.drawable.compose),null,
            modifier = Modifier.size(200.dp))
        Spacer(modifier = Modifier.height(10.dp))
        Text("Jetpack Compose", fontSize = 18.sp,color = MaterialTheme.colorScheme.onSurfaceVariant)

        Spacer(modifier = Modifier.height(50.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2)
        ) {
            items(data) { item ->
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .border(1.dp,Color.LightGray, RoundedCornerShape(15.dp))
                        .padding(8.dp)
                        .clickable {

                            val route = when(item.first){
                                data[0].first->{ Routes.ProductHomeScreen.route }
                                data[1].first->{Routes.AllUsersScreen.route}
                                data[2].first->{Routes.ThemeScreen.route}
                                data[3].first->{Routes.SettingScreen.route}
                                else -> {Routes.MainScreen.route}
                            }
                            moveTo(route)
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(painterResource(item.second),null,tint = Color.Unspecified, modifier = Modifier.size(50.dp))
                    Spacer(modifier = Modifier.height(20.dp))
                    Text("${item.first}",color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPre() {
    EcomTheme {
        //MainScreen()
        val data = listOf<Pair<String,Int>>(
            Pair("Products",R.drawable.products),
            Pair("Users",R.drawable.users),
            Pair("Theme",R.drawable.theme),
            Pair("Setting",R.drawable.settings),
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Image(painterResource(R.drawable.compose),null,
                modifier = Modifier.size(200.dp))
            Spacer(modifier = Modifier.height(10.dp))
            Text("Jetpack Compose", fontSize = 18.sp,color = MaterialTheme.colorScheme.onSurfaceVariant)

            Spacer(modifier = Modifier.height(50.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                items(data) { item ->
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .border(1.dp,Color.LightGray, RoundedCornerShape(15.dp))
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(painterResource(item.second),null,tint = Color.Unspecified, modifier = Modifier.size(50.dp))
                        Spacer(modifier = Modifier.height(20.dp))
                        Text("${item.first}",color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }

    }
}