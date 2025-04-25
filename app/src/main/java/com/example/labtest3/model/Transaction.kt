package com.example.labtest3.model

data class Transaction(
    val id: Long = System.currentTimeMillis(),
    var title: String,
    var amount: Double,
    var category: String,
    var type: String, // "Income" or "Expense"
    val date: Long = System.currentTimeMillis()
)