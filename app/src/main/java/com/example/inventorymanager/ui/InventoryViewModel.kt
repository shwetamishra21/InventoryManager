package com.example.inventorymanager.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.inventorymanager.data.InventoryItem
import com.example.inventorymanager.data.InventoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class InventoryViewModel(private val repository: InventoryRepository) : ViewModel() {
    private val _items = MutableStateFlow<List<InventoryItem>>(emptyList())
    val items: StateFlow<List<InventoryItem>> = _items

    init {
        loadItems()
    }

    private fun loadItems() {
        viewModelScope.launch {
            _items.value = repository.getAllItems()
        }
    }

    fun addItem(name: String, quantity: Int, price: Double, threshold: Int) {
        viewModelScope.launch {
            repository.addItem(
                InventoryItem(
                    name = name,
                    quantity = quantity,
                    price = price,
                    lowStockThreshold = threshold
                )
            )
            loadItems()
        }
    }

    fun deleteItem(id: Int) {
        viewModelScope.launch {
            repository.deleteItem(id)
            loadItems()
        }
    }
}

class InventoryViewModelFactory(private val repository: InventoryRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InventoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
