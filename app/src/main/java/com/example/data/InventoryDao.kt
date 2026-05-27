package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface InventoryDao {
    @Query("SELECT * FROM inventory_items ORDER BY itemName ASC")
    fun getAllItems(): Flow<List<InventoryItem>>

    @Insert
    suspend fun insertItem(item: InventoryItem)

    @Insert
    suspend fun insertTransaction(transaction: InventoryTransaction)

    @Update
    suspend fun updateItem(item: InventoryItem)

    @Query("SELECT * FROM inventory_transactions WHERE transactionType = 'OUT' ORDER BY timestamp ASC")
    fun getOutTransactions(): Flow<List<InventoryTransaction>>

    @Query("SELECT * FROM inventory_transactions WHERE itemId = :id ORDER BY timestamp DESC")
    fun getTransactionsForItem(id: Int): Flow<List<InventoryTransaction>>

    @Query("SELECT * FROM inventory_items WHERE itemId = :id LIMIT 1")
    suspend fun getItemById(id: Int): InventoryItem?

    @Transaction
    suspend fun adjustStock(itemId: Int, quantityChange: Int, type: String) {
        val item = getItemById(itemId)
        if (item != null) {
            val newStock = if (type == "IN") item.currentStock + quantityChange else item.currentStock - quantityChange
            if (newStock >= 0) {
                updateItem(item.copy(currentStock = newStock))
                insertTransaction(InventoryTransaction(itemId = itemId, quantityChanged = quantityChange, transactionType = type))
            }
        }
    }
}
