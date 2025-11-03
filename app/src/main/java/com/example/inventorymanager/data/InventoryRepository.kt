package com.example.inventorymanager.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InventoryRepository(private val dbHelper: InventoryDatabaseHelper) {

    suspend fun addItem(item: InventoryItem): Long = withContext(Dispatchers.IO) {
        dbHelper.insertItem(item)
    }

    suspend fun updateItem(item: InventoryItem): Int = withContext(Dispatchers.IO) {
        dbHelper.updateItem(item)
    }

    suspend fun deleteItem(id: Int): Int = withContext(Dispatchers.IO) {
        dbHelper.deleteItem(id)
    }

    suspend fun getAllItems(): List<InventoryItem> = withContext(Dispatchers.IO) {
        dbHelper.getAllItems()
    }

    suspend fun getItem(id: Int): InventoryItem? = withContext(Dispatchers.IO) {
        dbHelper.getItemById(id)
    }
}
