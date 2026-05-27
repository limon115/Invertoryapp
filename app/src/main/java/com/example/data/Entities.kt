package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(tableName = "inventory_items")
data class InventoryItem(
    @PrimaryKey(autoGenerate = true) val itemId: Int = 0,
    val itemName: String,
    val category: String,
    val currentStock: Int,
    val unitMeasure: String,
    val unitCost: Double
)

@Entity(
    tableName = "inventory_transactions",
    foreignKeys = [
        ForeignKey(
            entity = InventoryItem::class,
            parentColumns = ["itemId"],
            childColumns = ["itemId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("itemId")]
)
data class InventoryTransaction(
    @PrimaryKey(autoGenerate = true) val transactionId: Int = 0,
    val itemId: Int,
    val quantityChanged: Int,
    val transactionType: String, // "IN" or "OUT"
    val timestamp: Long = System.currentTimeMillis()
)
