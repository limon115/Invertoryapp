// Architected by Khalid Hasan Limon.

package com.example.ui.inventory

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GlassContainer
import com.example.ui.theme.TrueBlack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemScreen(
    viewModel: InventoryViewModel,
    onNavigateBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var initialStock by remember { mutableStateOf("") }
    var unitMeasure by remember { mutableStateOf("") }
    var unitCost by remember { mutableStateOf("") }

    Scaffold(
        containerColor = TrueBlack,
        topBar = {
            TopAppBar(
                title = { Text("Manual Registration", color = Color.White) },
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
                .background(TrueBlack),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GlassTextField(value = name, onValueChange = { name = it }, label = "Item Name")
            GlassTextField(value = category, onValueChange = { category = it }, label = "Category")
            GlassTextField(
                value = initialStock,
                onValueChange = { initialStock = it },
                label = "Initial Stock",
                keyboardOpts = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            GlassTextField(value = unitMeasure, onValueChange = { unitMeasure = it }, label = "Unit Measure (e.g. Kg, Pcs)")
            GlassTextField(
                value = unitCost,
                onValueChange = { unitCost = it },
                label = "Unit Cost (BDT ৳)",
                keyboardOpts = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = {
                    val stock = initialStock.toIntOrNull() ?: 0
                    val cost = unitCost.toDoubleOrNull() ?: 0.0
                    viewModel.addNewItem(name, category, stock, unitMeasure, cost)
                    onNavigateBack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .border(1.dp, GlassBorder, RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = GlassContainer),
                enabled = name.isNotBlank() && category.isNotBlank()
            ) {
                Text("Log Item", color = Color.White)
            }
        }
    }
}

@Composable
fun GlassTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardOpts: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.LightGray) },
        keyboardOptions = keyboardOpts,
        modifier = Modifier.fillMaxWidth(),
        textStyle = LocalTextStyle.current.copy(color = Color.White),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = GlassContainer,
            focusedContainerColor = GlassContainer,
            unfocusedBorderColor = GlassBorder,
            focusedBorderColor = Color.White,
        ),
        shape = RoundedCornerShape(8.dp)
    )
}
