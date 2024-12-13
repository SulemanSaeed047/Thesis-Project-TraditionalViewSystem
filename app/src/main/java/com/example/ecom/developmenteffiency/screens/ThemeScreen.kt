package com.example.ecom.developmenteffiency.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ecom.developmenteffiency.ViewModels.DEViewModel
import com.example.ecom.R

@Composable
fun ThemeScreen(viewmodel: DEViewModel){
    val isDarkTheme by viewmodel.isDarkTheme.observeAsState()
    val themeOptions: List<String> = listOf("Light Theme", "Dark Theme")
    var selectedTheme by remember { mutableStateOf(themeOptions[0]) }

    LazyColumn(
        modifier = Modifier.padding(12.dp)
    ) {
        item {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Dark Theme ", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Switch(
                    checked = isDarkTheme == true,
                    onCheckedChange = {
                        viewmodel.setTheme(it,"TS")
                    }
                )
            }
            Spacer( modifier = Modifier.height(20.dp))
            Divider()
            Spacer( modifier = Modifier.height(10.dp))
            Text(
                "Preview ",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth()
                )
            Spacer( modifier = Modifier.height(5.dp))
            ThemeSelector(themeOptions,selectedTheme){
                selectedTheme = it
            }
            Image(
                painterResource(if (selectedTheme==themeOptions[0]) R.drawable.light_theme else R.drawable.dark_theme),null,
                modifier = Modifier
                    .padding(18.dp)
                    .fillMaxWidth()
                    .border(1.dp, Color.LightGray)
            )
        }
    }


}

@Composable
fun ThemeSelector(
    themeOptions: List<String>,
    selectedTheme:String,
    onSelectionChange:(String)->Unit
    ) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
    ) {
        themeOptions.forEach { themeOption ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                RadioButton(
                    selected = selectedTheme == themeOption,
                    onClick = { onSelectionChange(themeOption) }
                )
                Text(
                    text = themeOption,
                    modifier = Modifier.padding(start = 8.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
