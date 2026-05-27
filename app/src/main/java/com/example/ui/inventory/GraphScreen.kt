// Architected by Khalid Hasan Limon.

package com.example.ui.inventory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.data.InventoryTransaction
import com.example.ui.theme.TrueBlack
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GraphScreen(
    viewModel: InventoryViewModel,
    onNavigateBack: () -> Unit
) {
    val outTransactions by viewModel.outTransactions.collectAsState()

    // Process transactions for graph
    // X-axis: simply index or days, Y-axis: quantity out
    val modelProducer = remember(outTransactions) {
        val entries = outTransactions.mapIndexed { index, tx ->
            FloatEntry(x = index.toFloat(), y = tx.quantityChanged.toFloat())
        }
        ChartEntryModelProducer(if (entries.isEmpty()) listOf(FloatEntry(0f, 0f)) else entries)
    }

    Scaffold(
        containerColor = TrueBlack,
        topBar = {
            TopAppBar(
                title = { Text("Historical Stock Usage (OUT)", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = TrueBlack)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .background(TrueBlack)
        ) {
            if (outTransactions.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No OUT transactions exist yet.", color = Color.Gray)
                }
            } else {
                Text("Total Units Used Trend", style = MaterialTheme.typography.titleLarge, color = Color.White)
                Spacer(modifier = Modifier.height(24.dp))
                Chart(
                    chart = lineChart(),
                    chartModelProducer = modelProducer,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            }
        }
    }
}
