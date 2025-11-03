package com.example.inventorymanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.inventorymanager.data.InventoryDatabaseHelper
import com.example.inventorymanager.data.InventoryRepository
import com.example.inventorymanager.ui.InventoryViewModel
import com.example.inventorymanager.ui.InventoryViewModelFactory
import com.example.inventorymanager.ui.screens.AddOrUpdateItemScreen
import com.example.inventorymanager.ui.screens.ItemListScreen
import com.example.inventorymanager.ui.screens.LandingScreen
import com.example.inventorymanager.ui.theme.InventoryManagerTheme
import androidx.compose.runtime.collectAsState

sealed class Screen(val route: String) {
    object Landing : Screen("landing")
    object InventoryList : Screen("inventory_list")
    object AddItem : Screen("add_item")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dbHelper = InventoryDatabaseHelper(this)
        val repository = InventoryRepository(dbHelper)
        val factory = InventoryViewModelFactory(repository)

        setContent {
            InventoryManagerTheme {
                val navController: NavHostController = rememberNavController()
                val viewModel: InventoryViewModel = viewModel(factory = factory)
                val items by viewModel.items.collectAsState()

                NavHost(
                    navController = navController,
                    startDestination = Screen.Landing.route
                ) {
                    composable(Screen.Landing.route) {
                        LandingScreen(onStart = {
                            navController.navigate(Screen.InventoryList.route) {
                                popUpTo(Screen.Landing.route) { inclusive = true }
                            }
                        })
                    }
                    composable(Screen.InventoryList.route) {
                        ItemListScreen(
                            items = items,
                            onAddClick = { navController.navigate(Screen.AddItem.route) },
                            onDeleteClick = { item -> viewModel.deleteItem(item.id) },
                            onIncrement = { item -> viewModel.incrementItem(item) },
                            onDecrement = { item -> viewModel.decrementItem(item) },
                            onEditClick = { item ->
                                // Navigate to AddItem screen for editing (pass item if needed)
                                // For simple integration, navigate and pass id or navigate with custom logic
                                // Here we just navigate to add item screen, you can enhance to pass item data
                                navController.navigate(Screen.AddItem.route)
                            }
                        )
                    }
                    composable(Screen.AddItem.route) {
                        AddOrUpdateItemScreen(
                            onSave = { name, qty, price, threshold ->
                                // Ideally, check if editing an existing item; for now, always add new
                                viewModel.addItem(name, qty, price, threshold)
                                navController.popBackStack()
                            },
                            onCancel = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}
