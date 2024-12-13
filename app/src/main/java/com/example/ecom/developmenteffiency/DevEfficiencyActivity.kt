package com.example.ecom.developmenteffiency

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Debug
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.example.ecom.developmenteffiency.ViewModels.DEViewModel
import com.example.ecom.R
import com.example.ecom.developmenteffiency.Routes.AppNavigationController
import com.example.ecom.ui.theme.EcomTheme

class DevEfficiencyActivity : ComponentActivity() {

    val viewModel: DEViewModel by viewModels()
    private lateinit var internetChangeReceiver: BroadcastReceiver
    var init= true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Start tracing with a file name and buffer size
        Debug.startMethodTracing("app_startup_trace", 16 * 1024 * 1024) // 16MB buffer
        val startTime = System.nanoTime()
        setContent {

            val navController = rememberNavController()
            if (init) {
                viewModel.setTheme(isSystemInDarkTheme(),"DE")
                init = false
            }

            var isDarkTheme = viewModel.isDarkTheme.observeAsState()
            Log.d("theme DE", isDarkTheme.value.toString())

            EcomTheme(
                darkTheme = isDarkTheme.value == true,
                dynamicColor = false
            ) {
                Column(
                    modifier = Modifier.background(MaterialTheme.colorScheme.secondary)
                ) {
                    AppNavigationController(
                        navController = navController,
                        viewModel = viewModel
                    )
                }
            }
        }

        // Stop tracing after UI initialization is complete
        Debug.stopMethodTracing()

        val endTime = System.nanoTime()
        val startupTime = (endTime - startTime) / 1_000_000  // Convert to milliseconds
        Log.d("StartupTime", "Compose Layout Startup Time: ${startupTime} ms")
        viewModel.checkOnline(this)
        internetChangeReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == ConnectivityManager.CONNECTIVITY_ACTION) {
                    viewModel.checkOnline(this@DevEfficiencyActivity)
                }
            }
        }

    }
    override fun onResume() {
        super.onResume()
        val ifilter = IntentFilter()
        val receiverFlags = ContextCompat.RECEIVER_EXPORTED
        ifilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        ContextCompat.registerReceiver(this,internetChangeReceiver, ifilter,receiverFlags)
        ////
        /* val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
         registerReceiver(internetChangeReceiver, filter)*/
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(internetChangeReceiver)
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    EcomTheme {}
}

@Composable
fun CounterParent() {
    var count by remember { mutableStateOf(0) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        Text(
            text = "Parent Composable: Managing State",
            color = Color.DarkGray,
            fontSize = 20.sp,
            modifier = Modifier.padding(8.dp)
        )
        CounterDisplay(count = count, onIncrement = { count++ })
    }
}
@Composable
fun CounterDisplay(count: Int, onIncrement: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Stateless Counter Display",
            color = Color.Black,
            fontSize = 18.sp
        )
        Text(
            text = "Count: $count",
            color = Color.Blue,
            fontSize = 28.sp,
            modifier = Modifier.padding(8.dp)
        )
        Button(onClick = onIncrement, modifier = Modifier.padding(top = 8.dp)) {
            Text("Increment Count")
        }
    }
}

@Composable
fun Counter() {
    var count by remember { mutableStateOf(0) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().background(Color.LightGray)
    ) {
        Text(
            text = "Stateful Counter Component",
            color = Color.DarkGray,
            fontSize = 20.sp,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = "Count: $count",
            color = Color.Blue,
            fontSize = 28.sp,
            modifier = Modifier.padding(8.dp)
        )
        Button(onClick = { count++ }, modifier = Modifier.padding(top = 8.dp)) {
            Text("Increment Count")
        }
    }
}