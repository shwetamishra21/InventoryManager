package com.example.inventorymanager.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inventorymanager.data.InventoryItem
import com.example.inventorymanager.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemListScreen(
    items: List<InventoryItem>,
    onAddClick: () -> Unit,
    onDeleteClick: (InventoryItem) -> Unit,
    onEditClick: (InventoryItem) -> Unit,
    onIncrement: (InventoryItem) -> Unit,
    onDecrement: (InventoryItem) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = GreenSecondary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Item", tint = GreenOnPrimary)
            }
        },
        containerColor = BackgroundDark
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Inventory",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = AccentYellow
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (items.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No items yet.", color = OnBackgroundDark)
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(items) { item ->
                        val isLow = item.quantity <= item.lowStockThreshold
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.medium,
                            colors = CardDefaults.cardColors(
                                containerColor = if (isLow) ErrorRed.copy(alpha = 0.07f) else CardDark
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(item.name, fontWeight = FontWeight.Bold, color = GreenOnPrimary)
                                    Text("Qty: ${item.quantity}  |  ₹${item.price}", color = OnBackgroundDark)
                                    if (isLow) {
                                        Text("Low stock!", color = ErrorRed, fontWeight = FontWeight.Bold)
                                    }
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(onClick = { onDecrement(item) }) {
                                        Text(
                                            text = "-",
                                            fontSize = 24.sp,
                                            color = GreenSecondary,
                                            modifier = Modifier.padding(horizontal = 4.dp)
                                        )
                                    }
                                    Text(
                                        "${item.quantity}",
                                        fontWeight = FontWeight.Bold,
                                        color = AccentYellow,
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    )
                                    IconButton(onClick = { onIncrement(item) }) {
                                        Icon(Icons.Filled.Add, contentDescription = "Increment", tint = GreenSecondary)
                                    }
                                    IconButton(onClick = { onEditClick(item) }) {
                                        Icon(Icons.Filled.Edit, contentDescription = "Edit", tint = GreenSecondary)
                                    }
                                    IconButton(onClick = { onDeleteClick(item) }) {
                                        Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = ErrorRed)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
