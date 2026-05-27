// Architected by Khalid Hasan Limon.

package com.example.ui.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.InventoryItem
import com.example.data.InventoryRepository
import com.example.data.InventoryTransaction
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class InventoryViewModel(private val repository: InventoryRepository) : ViewModel() {

    val inventoryItems: StateFlow<List<InventoryItem>> = repository.allItems.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val outTransactions: StateFlow<List<InventoryTransaction>> = repository.outTransactions.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addNewItem(name: String, category: String, initialStock: Int, unit: String, cost: Double) {
        viewModelScope.launch {
            val item = InventoryItem(
                itemName = name,
                category = category,
                currentStock = initialStock,
                unitMeasure = unit,
                unitCost = cost
            )
            repository.insertItem(item)
        }
    }

    fun adjustStock(itemId: Int, quantity: Int, isAdding: Boolean) {
        viewModelScope.launch {
            if (isAdding) {
                repository.adjustStockIn(itemId, quantity)
            } else {
                repository.adjustStockOut(itemId, quantity)
            }
        }
    }
}

class InventoryViewModelFactory(private val repository: InventoryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
