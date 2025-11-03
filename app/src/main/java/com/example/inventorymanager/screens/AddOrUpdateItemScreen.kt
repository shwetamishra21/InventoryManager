package com.example.inventorymanager.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.inventorymanager.data.InventoryItem

@Composable
fun AddOrUpdateItemScreen(
    existingItem: InventoryItem? = null,
    onSave: (String, Int, Double, Int) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf(existingItem?.name ?: "") }
    var quantity by remember { mutableStateOf(existingItem?.quantity?.toString() ?: "") }
    var price by remember { mutableStateOf(existingItem?.price?.toString() ?: "") }
    var threshold by remember { mutableStateOf(existingItem?.lowStockThreshold?.toString() ?: "") }

    val isEdit = existingItem != null

    Column(
        modifier = Modifier.padding(24.dp).fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            if (isEdit) "Update Item" else "Add Item",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Item Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = quantity,
            onValueChange = { quantity = it },
            label = { Text("Quantity") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price (₹)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = threshold,
            onValueChange = { threshold = it },
            label = { Text("Low Stock Threshold") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(
                onClick = onCancel,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("Cancel")
            }
            Button(
                onClick = {
                    val q = quantity.toIntOrNull() ?: 0
                    val p = price.toDoubleOrNull() ?: 0.0
                    val t = threshold.toIntOrNull() ?: 0
                    if (name.isNotBlank()) {
                        onSave(name, q, p, t)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(if (isEdit) "Update" else "Save", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}
