package com.example.ecom.developmenteffiency.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ecom.developmenteffiency.ViewModels.DEViewModel
import com.example.ecom.R
import com.example.ecom.developmenteffiency.API.ModelClasses.ProductsList


@Composable
fun ProductHomeScreen(viewModel: DEViewModel, onPrevClick:(Int)->Unit ) {
    var productList = viewModel.productList.observeAsState()
    var prodCategoriesList = viewModel.prodCategoriesList.observeAsState()
    var selectedFilter = rememberSaveable { mutableStateOf("All") }
    val context = LocalContext.current
    var isOnline = viewModel.isOnline.observeAsState()
    var isLoading = viewModel.isLoading.observeAsState()
    LaunchedEffect(true) {
        viewModel.getAllProducts(selectedFilter.value)
        viewModel.getAllCategories()
    }


    Box(modifier = Modifier.fillMaxSize().padding(12.dp)){
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            //if(isOnline.value == true){
            if(isLoading.value == true){
                item {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier.width(64.dp).align(Alignment.Center)
                                .align(Alignment.Center)
                        )
                    }
                    /*offLineScrree(){
                        var i = Intent(Settings.ACTION_WIFI_SETTINGS)
                        context.startActivity(i)
                    }*/
                }

            }else{
                item {
                    LazyRow {
                        items(prodCategoriesList.value ?: emptyList()){
                            Row( horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .heightIn(min = 40.dp)
                                    .widthIn(min = 70.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(
                                        color = if (selectedFilter.value == it.trim()) MaterialTheme.colorScheme.primary else Color.Transparent,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .border(0.5.dp,
                                        if (selectedFilter.value == it.trim()) MaterialTheme.colorScheme.primary else Color.Gray,
                                        RoundedCornerShape(12.dp))
                                    .clickable {
                                        selectedFilter.value = it.trim()
                                        viewModel.getAllProducts(selectedFilter.value)
                                    }
                                    .padding(12.dp) ) {
                                Text(text = it,
                                    color = if (selectedFilter.value == it.trim()) MaterialTheme.colorScheme.onPrimary
                                    else MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
                itemsIndexed(productList.value ?: emptyList()){ index, product->
                    ProductItem(product){  onPrevClick(it)  }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun offLineScrree(onRetryClick:()->Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()) {
        AsyncImage( model =  R.drawable.ic_no_internet, contentDescription = null,contentScale = ContentScale.Fit,
            modifier = Modifier.size(150.dp),
        )

        Button(onClick = {onRetryClick()}) {
            Text("Connect")
        }

    }
}

@Composable
fun ProductItem(product: ProductsList, onPrevClick:(Int)->Unit) {
    Card ( elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        colors =  CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant )){
        Row( modifier = Modifier.padding(7.dp).clickable { onPrevClick(product.id.toInt())}
        ) {
            Column( verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(3f).fillMaxSize()
            ) {
                AsyncImage( model =  product.image, contentDescription = null,contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize().aspectRatio(1f),
                )
                Text("$ ${product.price}",fontSize = 18.sp, fontWeight = FontWeight.ExtraBold,modifier = Modifier.padding(top = 20.dp))
            }
            Column(modifier = Modifier.weight(7f).padding(start = 8.dp)) {
                Text(text = product.title, fontSize = 14.sp, fontWeight = FontWeight.Bold,lineHeight = 18.sp,maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 12.dp))
                Text(text = product.description, fontSize = 12.sp, maxLines = 3, lineHeight = 18.sp, overflow = TextOverflow.Ellipsis )
                Row( horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
                ) {
                    Icon(Icons.Default.Star,null, tint = Color(0xFFDA8505))
                    Text(" ${product.rating.rate}")
                }
            }
        }
    }
}

