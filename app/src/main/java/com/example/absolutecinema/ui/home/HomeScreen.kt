package com.example.absolutecinema.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.absolutecinema.ui.navigation.NavigationDestination

object HomePage : NavigationDestination {
    override val route = "home"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    Card(
        modifier = Modifier
            .padding(start = 40.dp, end = 40.dp, top = 300.dp, bottom = 100.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 80.dp)
        ) {
            Text("Entered")
        }
    }
}
