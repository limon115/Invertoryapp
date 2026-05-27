// Architected by Khalid Hasan Limon.

package com.example.ui.inventory

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

object Routes {
    const val DASHBOARD = "dashboard"
    const val ADD_ITEM = "add_item"
    const val GRAPH = "graph"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PolwelNavigation(viewModel: InventoryViewModel) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val exportService = remember { ExportService() }
    val coroutineScope = rememberCoroutineScope()
    var showExportDialog by remember { mutableStateOf(false) }

    val items by viewModel.inventoryItems.collectAsState()

    if (showExportDialog) {
        AlertDialog(
            onDismissRequest = { showExportDialog = false },
            title = { Text("Export Report") },
            text = { Text("Choose export format for PDF Report:") },
            confirmButton = {
                Button(onClick = {
                    showExportDialog = false
                    coroutineScope.launch {
                        exportService.exportToPdf(context, items, isDigitalMode = true)
                    }
                }) {
                    Text("Digital (Color)")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = {
                    showExportDialog = false
                    coroutineScope.launch {
                        exportService.exportToPdf(context, items, isDigitalMode = false)
                    }
                }) {
                    Text("Print (B&W)")
                }
            }
        )
    }

    NavHost(
        navController = navController,
        startDestination = Routes.DASHBOARD,
        modifier = Modifier.background(Color.Black)
    ) {
        composable(Routes.DASHBOARD) {
            DashboardScreen(
                viewModel = viewModel,
                onNavigateToAdd = { navController.navigate(Routes.ADD_ITEM) },
                onNavigateToGraph = { navController.navigate(Routes.GRAPH) },
                onExportReports = { showExportDialog = true }
            )
        }
        composable(Routes.ADD_ITEM) {
            AddItemScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Routes.GRAPH) {
            GraphScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
