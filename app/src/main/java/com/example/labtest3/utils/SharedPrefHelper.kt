package com.example.labtest3.utils

import android.content.Context
import com.example.labtest3.model.Transaction
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPrefHelper(context: Context) {
    private val prefs = context.getSharedPreferences("finance_data", Context.MODE_PRIVATE)
    private val editor = prefs.edit()
    private val gson = Gson()

    // Save a new transaction
    fun saveTransaction(transaction: Transaction) {
        val currentList = getTransactions().toMutableList()
        currentList.add(transaction)
        val json = gson.toJson(currentList)
        editor.putString("transactions", json).apply()
    }

    // Retrieve all transactions
    fun getTransactions(): List<Transaction> {
        val json = prefs.getString("transactions", "[]")
        val type = object : TypeToken<List<Transaction>>() {}.type
        return gson.fromJson(json, type)
    }

    // Save budget
    fun setBudget(amount: Double) {
        editor.putFloat("budget", amount.toFloat()).apply()
    }

    // Get budget
    fun getBudget(): Double {
        return prefs.getFloat("budget", 0f).toDouble()
    }

    // Get current balance
    fun getCurrentBalance(): Double {
        val list = getTransactions()
        val income = list.filter { it.type.equals("income", ignoreCase = true) }
            .sumOf { it.amount }
        val expense = list.filter { it.type.equals("expense", ignoreCase = true) }
            .sumOf { it.amount }
        return income - expense
    }

    // Update a transaction
    fun updateTransaction(updated: Transaction) {
        val list = getTransactions().toMutableList()
        val index = list.indexOfFirst { it.id == updated.id }
        if (index != -1) {
            list[index] = updated
            editor.putString("transactions", gson.toJson(list)).apply()
        }
    }

    // Delete a transaction
    fun deleteTransaction(id: Long) {
        val list = getTransactions().filter { it.id != id }
        editor.putString("transactions", gson.toJson(list)).apply()
    }

    // ✅ NEW: Check if expenses exceed budget
    fun isExpenseOverBudget(): Boolean {
        val budget = getBudget()
        val totalExpense = getTransactions()
            .filter { it.type.equals("expense", ignoreCase = true) }
            .sumOf { it.amount }
        return totalExpense > budget
    }
}
