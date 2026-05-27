package com.example.data

import kotlinx.coroutines.flow.Flow

class InventoryRepository(private val dao: InventoryDao) {

    val allItems: Flow<List<InventoryItem>> = dao.getAllItems()
    val outTransactions: Flow<List<InventoryTransaction>> = dao.getOutTransactions()

    suspend fun insertItem(item: InventoryItem) {
        dao.insertItem(item)
    }

    suspend fun adjustStockIn(itemId: Int, quantity: Int) {
        dao.adjustStock(itemId, quantity, "IN")
    }

    suspend fun adjustStockOut(itemId: Int, quantity: Int) {
        dao.adjustStock(itemId, quantity, "OUT")
    }
}
