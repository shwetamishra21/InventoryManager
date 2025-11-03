package com.example.inventorymanager.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class InventoryDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "inventory.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE inventory (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "quantity INTEGER NOT NULL," +
                    "price REAL NOT NULL," +
                    "lowStockThreshold INTEGER NOT NULL" +
                    ")"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS inventory")
        onCreate(db)
    }

    fun insertItem(item: InventoryItem): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", item.name)
            put("quantity", item.quantity)
            put("price", item.price)
            put("lowStockThreshold", item.lowStockThreshold)
        }
        val id = db.insert("inventory", null, values)
        db.close()
        return id
    }

    fun updateItem(item: InventoryItem): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", item.name)
            put("quantity", item.quantity)
            put("price", item.price)
            put("lowStockThreshold", item.lowStockThreshold)
        }
        val rows = db.update("inventory", values, "id = ?", arrayOf(item.id.toString()))
        db.close()
        return rows
    }

    fun deleteItem(id: Int): Int {
        val db = writableDatabase
        val rows = db.delete("inventory", "id = ?", arrayOf(id.toString()))
        db.close()
        return rows
    }

    fun getAllItems(): List<InventoryItem> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM inventory", null)
        val items = mutableListOf<InventoryItem>()
        while (cursor.moveToNext()) {
            val item = InventoryItem(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity")),
                price = cursor.getDouble(cursor.getColumnIndexOrThrow("price")),
                lowStockThreshold = cursor.getInt(cursor.getColumnIndexOrThrow("lowStockThreshold"))
            )
            items.add(item)
        }
        cursor.close()
        db.close()
        return items
    }

    fun getItemById(id: Int): InventoryItem? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM inventory WHERE id = ?",
            arrayOf(id.toString())
        )
        var item: InventoryItem? = null
        if (cursor.moveToFirst()) {
            item = InventoryItem(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity")),
                price = cursor.getDouble(cursor.getColumnIndexOrThrow("price")),
                lowStockThreshold = cursor.getInt(cursor.getColumnIndexOrThrow("lowStockThreshold"))
            )
        }
        cursor.close()
        db.close()
        return item
    }
}
