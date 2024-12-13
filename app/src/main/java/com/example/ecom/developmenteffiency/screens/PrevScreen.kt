package com.example.ecom.developmenteffiency.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ecom.developmenteffiency.ViewModels.DEViewModel
import com.example.ecom.developmenteffiency.API.ModelClasses.ProductsList


@Composable
fun PrevScreen(productId:Int?,viewModel: DEViewModel) {
    var productList = viewModel.productList.observeAsState(emptyList())
    val selectedProduct: ProductsList? = productList.value.find { it.id == productId?.toLong() }
    if (selectedProduct != null) {
        ProductPrev(selectedProduct)
    } else {
        Text(text = "Product not found", color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
@Composable
fun ProductPrev(selectedProduct: ProductsList) {
    Box(modifier = Modifier.fillMaxSize().padding(12.dp)){
        LazyColumn {
            item {
                AsyncImage( model =  selectedProduct.image, contentDescription = null, /*contentScale = ContentScale.Fit,*/ )
                Text( selectedProduct.title,fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(top = 20.dp))
                Row( horizontalArrangement = Arrangement.Absolute.SpaceBetween, modifier = Modifier.fillMaxWidth() ) {
                    Column(  horizontalAlignment = Alignment.CenterHorizontally  )  {
                        Text("Price",fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 20.dp, bottom = 5.dp))
                        Text("$ ${selectedProduct.price}",fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.ExtraBold,)
                    }
                    Column( horizontalAlignment = Alignment.CenterHorizontally )  {
                        Text("Rating",fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 20.dp, bottom = 5.dp))
                        Row(  verticalAlignment = Alignment.CenterVertically, ){
                            Icon(Icons.Default.Star,null, tint = Color(0xFFDA8505))
                            Text(" ${selectedProduct.rating.rate} ", color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 14.sp, fontWeight = FontWeight.ExtraBold,  )
                        }
                    }
                    Column(  horizontalAlignment = Alignment.CenterHorizontally  ) {
                        Text("counts",fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 20.dp, bottom = 5.dp))
                        Text(selectedProduct.rating.count.toString(), color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, )
                    }
                }
                Text("Description",fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.ExtraBold,  modifier = Modifier.padding(top = 20.dp))
                Text(selectedProduct.description,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 13.sp, /*textAlign = TextAlign.Justify,*/
                    modifier = Modifier.padding(top = 20.dp))
            }
        }
    }
}

