package com.example.inventorymanager.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.inventorymanager.data.InventoryItem

@Composable
fun InventoryScreen(
    items: List<InventoryItem>,
    onAddItem: (String, Int, Double) -> Unit,
    onDeleteItem: (InventoryItem) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var qty by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF4E0))
            .padding(16.dp)
    ) {
        Text(
            "Inventory Manager",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = Color(0xFFCC7A00)
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Item Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = qty,
            onValueChange = { qty = it },
            label = { Text("Quantity") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        Button(
            onClick = {
                if (name.isNotBlank() && qty.isNotBlank() && price.isNotBlank()) {
                    onAddItem(name, qty.toIntOrNull() ?: 0, price.toDoubleOrNull() ?: 0.0)
                    name = ""; qty = ""; price = ""
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFB366)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Item", color = Color.White)
        }

        Spacer(Modifier.height(20.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(items) { item ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE5CC)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(item.name, fontWeight = FontWeight.Bold)
                            Text("Qty: ${item.quantity}  |  ₹${item.price}")
                        }
                        TextButton(onClick = { onDeleteItem(item) }) {
                            Text("Delete", color = Color.Red)
                        }
                    }
                }
            }
        }
    }
}
