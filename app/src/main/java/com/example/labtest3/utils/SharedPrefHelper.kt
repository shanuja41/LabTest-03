package com.example.labtest3.utils

import android.content.Context
import com.example.labtest3.model.Transaction
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPrefHelper(context: Context) {
    private val prefs = context.getSharedPreferences("finance_data", Context.MODE_PRIVATE)
    private val editor = prefs.edit()
    private val gson = Gson()  // Make sure this comes from com.google.gson.*

    fun saveTransaction(transaction: Transaction) {
        val currentList = getTransactions().toMutableList()
        currentList.add(transaction)
        val json = gson.toJson(currentList)
        editor.putString("transactions", json).apply()
    }

    fun getTransactions(): List<Transaction> {
        val json = prefs.getString("transactions", "[]")
        val type = object : TypeToken<List<Transaction>>() {}.type
        return gson.fromJson(json, type)
    }

    fun setBudget(amount: Double) {
        editor.putFloat("budget", amount.toFloat()).apply()
    }

    fun getBudget(): Double {
        return prefs.getFloat("budget", 0f).toDouble()
    }

    fun getCurrentBalance(): Double {
        val list = getTransactions()
        val income = list.filter { it.type == "Income" }.sumOf { it.amount }
        val expense = list.filter { it.type == "Expense" }.sumOf { it.amount }
        return income - expense
    }

    fun updateTransaction(updated: Transaction) {
        val list = getTransactions().toMutableList()
        val index = list.indexOfFirst { it.id == updated.id }
        if (index != -1) {
            list[index] = updated
            editor.putString("transactions", gson.toJson(list)).apply()
        }
    }

    fun deleteTransaction(id: Long) {
        val list = getTransactions().filter { it.id != id }
        editor.putString("transactions", gson.toJson(list)).apply()
    }

}
