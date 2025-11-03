package com.example.inventorymanager.data

data class InventoryItem(
    var id: Int = 0,
    val name: String,
    val quantity: Int,
    val price: Double,
    val lowStockThreshold: Int = 0
)
