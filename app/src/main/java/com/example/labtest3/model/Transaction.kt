package com.example.labtest3.model

data class Transaction(
    val id: Long = System.currentTimeMillis(),
    val title: String,
    val amount: Double,
    val category: String,
    val type: String, // "Income" or "Expense"
    val date: Long = System.currentTimeMillis()
)