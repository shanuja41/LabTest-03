package com.example.labtest3

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.labtest3.adapter.TransactionAdapter
import com.example.labtest3.model.Transaction
import com.example.labtest3.utils.SharedPrefHelper

class activity_dashboard : ComponentActivity() {

    private lateinit var tvBudget: TextView
    private lateinit var etBudget: EditText
    private lateinit var btnSetBudget: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnViewTransactions: Button
    private lateinit var tvCurrentBalance: TextView
    private lateinit var btnExpense: LinearLayout
    private lateinit var btnIncome: LinearLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Linking Views
        tvBudget = findViewById(R.id.tv_budget)
        etBudget = findViewById(R.id.et_budget)
        btnSetBudget = findViewById(R.id.btn_set_budget)
        recyclerView = findViewById(R.id.rv_transactions)
        btnViewTransactions = findViewById(R.id.btn_view_transactions)
        tvCurrentBalance = findViewById(R.id.tv_current_balance)
        btnExpense = findViewById(R.id.btn_expense)
        btnIncome = findViewById(R.id.btn_income)

        // Setup RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        val pref = SharedPrefHelper(this)
        val transactions = pref.getTransactions() // Assuming this returns a List<Transaction>

        // Update: Add lambdas for edit and delete actions
        val adapter = TransactionAdapter(
            transactions,
            onEditClick = { transaction ->
                // Handle edit action (e.g., launch an edit screen or modify the transaction)
                Toast.makeText(this, "Edit clicked for: ${transaction.title}", Toast.LENGTH_SHORT).show()
                // Add your edit logic here
            },
            onDeleteClick = { transaction ->
                // Handle delete action
                pref.deleteTransaction(transaction.id)
                refreshTransactions()  // Update the transaction list after deletion
                Toast.makeText(this, "Transaction deleted", Toast.LENGTH_SHORT).show()
            }
        )
        recyclerView.adapter = adapter

        // Load saved budget
        val sharedPrefs = getSharedPreferences("finance_prefs", Context.MODE_PRIVATE)
        val savedBudget = sharedPrefs.getFloat("budget", 0f)
        tvBudget.text = "Budget: LKR %.2f".format(savedBudget)

        // Set Budget Button
        btnSetBudget.setOnClickListener {
            val input = etBudget.text.toString()
            if (input.isNotEmpty()) {
                val budgetValue = input.toFloat()
                sharedPrefs.edit().putFloat("budget", budgetValue).apply()
                tvBudget.text = "Budget: LKR %.2f".format(budgetValue)
                Toast.makeText(this, "Budget saved!", Toast.LENGTH_SHORT).show()
                etBudget.text.clear()
            } else {
                Toast.makeText(this, "Please enter a budget value", Toast.LENGTH_SHORT).show()
            }
        }

        // View Transactions Button
        btnViewTransactions.setOnClickListener {
            if (recyclerView.visibility == View.GONE) {
                recyclerView.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.GONE
            }
        }

        // Expense Button Action
        btnExpense.setOnClickListener {
            // Handle expense action here
            val intent = Intent(this, AddExpenseActivity::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(this, "Expense clicked", Toast.LENGTH_SHORT).show()
        }

        // Income Button Action
        btnIncome.setOnClickListener {
            // Handle income action here
            val intent = Intent(this, AddIncomeActivity::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(this, "Income clicked", Toast.LENGTH_SHORT).show()
        }
    }

    private fun refreshTransactions() {
        val pref = SharedPrefHelper(this)
        val transactions = pref.getTransactions() // Get updated list of transactions
        val adapter = TransactionAdapter(
            transactions,
            onEditClick = { transaction ->

//                val intent = Intent(this, activity_dashboard::class.java)
//                startActivity(intent)
//                finish()
                // Handle edit action (can be more specific based on your needs)
                Toast.makeText(this, "Edit clicked for: ${transaction.title}", Toast.LENGTH_SHORT).show()
            },
            onDeleteClick = { transaction ->
                // Handle delete action
                pref.deleteTransaction(transaction.id)
                Toast.makeText(this, "Transaction deleted", Toast.LENGTH_SHORT).show()
            }
        )
        recyclerView.adapter = adapter
    }
}
