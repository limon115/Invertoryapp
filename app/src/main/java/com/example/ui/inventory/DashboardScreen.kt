// Architected by Khalid Hasan Limon.

package com.example.ui.inventory

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.InventoryItem
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GlassContainer
import com.example.ui.theme.PositiveGreen
import com.example.ui.theme.NegativeRed
import com.example.ui.theme.TrueBlack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: InventoryViewModel,
    onNavigateToAdd: () -> Unit,
    onNavigateToGraph: () -> Unit,
    onExportReports: () -> Unit
) {
    val items by viewModel.inventoryItems.collectAsState()

    Scaffold(
        containerColor = TrueBlack,
        topBar = {
            TopAppBar(
                title = { Text("Polwel ERP Dashboard", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = TrueBlack),
                actions = {
                    IconButton(onClick = onNavigateToGraph) {
                        Icon(Icons.Default.BarChart, contentDescription = "Graphs", tint = Color.White)
                    }
                    IconButton(onClick = onExportReports) {
                        Icon(Icons.Default.Download, contentDescription = "Export Data", tint = Color.White)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAdd,
                containerColor = GlassContainer,
                contentColor = Color.White,
                modifier = Modifier.border(1.dp, GlassBorder, RoundedCornerShape(16.dp))
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Item")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(TrueBlack)
        ) {
            if (items.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No items recorded. Registration required.", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(items) { item ->
                        InventoryItemCard(
                            item = item,
                            onAdjust = { qty, isAdding -> viewModel.adjustStock(item.itemId, qty, isAdding) }
                        )
                    }
                    
                    item {
                        Spacer(modifier = Modifier.height(100.dp))
                        Text(
                            text = "Developed by Khalid Hasan Limon",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray,
                            modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InventoryItemCard(item: InventoryItem, onAdjust: (Int, Boolean) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(GlassContainer)
            .border(1.dp, GlassBorder, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.itemName,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Cat: ${item.category} | Cost: ৳${item.unitCost}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.LightGray
                )
            }
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = { onAdjust(1, false) },
                    modifier = Modifier
                        .size(36.dp)
                        .background(GlassContainer, RoundedCornerShape(8.dp))
                ) {
                    Icon(Icons.Default.Remove, contentDescription = "Deduct", tint = NegativeRed)
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = item.currentStock.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = item.unitMeasure,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                IconButton(
                    onClick = { onAdjust(1, true) },
                    modifier = Modifier
                        .size(36.dp)
                        .background(GlassContainer, RoundedCornerShape(8.dp))
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add", tint = PositiveGreen)
                }
            }
        }
    }
}
