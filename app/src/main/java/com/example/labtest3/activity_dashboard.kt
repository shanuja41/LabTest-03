package com.example.labtest3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.labtest3.adapter.TransactionAdapter
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

    private lateinit var pref: SharedPrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // View bindings
        tvBudget = findViewById(R.id.tv_budget)
        etBudget = findViewById(R.id.et_budget)
        btnSetBudget = findViewById(R.id.btn_set_budget)
        recyclerView = findViewById(R.id.rv_transactions)
        btnViewTransactions = findViewById(R.id.btn_view_transactions)
        tvCurrentBalance = findViewById(R.id.tv_current_balance)
        btnExpense = findViewById(R.id.btn_expense)
        btnIncome = findViewById(R.id.btn_income)

        recyclerView.layoutManager = LinearLayoutManager(this)
        pref = SharedPrefHelper(this)

        loadBudget()
        updateCurrentBalance()
        setRecyclerView()

        btnSetBudget.setOnClickListener {
            val input = etBudget.text.toString()
            if (input.isNotEmpty()) {
                val budgetValue = input.toDouble()
                pref.setBudget(budgetValue)
                tvBudget.text = "Budget: LKR %.2f".format(budgetValue)
                Toast.makeText(this, "Budget saved!", Toast.LENGTH_SHORT).show()
                etBudget.text.clear()
            } else {
                Toast.makeText(this, "Please enter a budget value", Toast.LENGTH_SHORT).show()
            }
        }

        btnViewTransactions.setOnClickListener {
            recyclerView.visibility =
                if (recyclerView.visibility == View.GONE) View.VISIBLE else View.GONE
        }

        btnExpense.setOnClickListener {
            startActivity(Intent(this, AddExpenseActivity::class.java))
        }

        btnIncome.setOnClickListener {
            startActivity(Intent(this, AddIncomeActivity::class.java))
        }
    }

    private fun loadBudget() {
        val savedBudget = pref.getBudget()
        tvBudget.text = "Budget: LKR %.2f".format(savedBudget)
    }

    private fun updateCurrentBalance() {
        val transactions = pref.getTransactions()
        var incomeTotal = 0.0
        var expenseTotal = 0.0

        for (transaction in transactions) {
            if (transaction.type.equals("income", ignoreCase = true)) {
                incomeTotal += transaction.amount
            } else if (transaction.type.equals("expense", ignoreCase = true)) {
                expenseTotal += transaction.amount
            }
        }

        val currentBalance = incomeTotal - expenseTotal
        tvCurrentBalance.text = "Current Balance: LKR %.2f".format(currentBalance)
    }

    private fun setRecyclerView() {
        val transactions = pref.getTransactions()

        val adapter = TransactionAdapter(
            transactions,
            onEditClick = { transaction ->
                val intent = Intent(this, EditTransaction::class.java)
                startActivity(intent)
                Toast.makeText(this, "Edit clicked for: ${transaction.title}", Toast.LENGTH_SHORT).show()
            },
            onDeleteClick = { transaction ->
                pref.deleteTransaction(transaction.id)
                refreshTransactions()
                Toast.makeText(this, "Transaction deleted", Toast.LENGTH_SHORT).show()
            }
        )

        recyclerView.adapter = adapter
    }

    private fun refreshTransactions() {
        setRecyclerView()
        updateCurrentBalance()
    }

    private fun calculateTotalExpense(): Double {
        val transactions = pref.getTransactions()
        var expenseTotal = 0.0
        for (transaction in transactions) {
            if (transaction.type.equals("expense", ignoreCase = true)) {
                expenseTotal += transaction.amount
            }
        }
        return expenseTotal
    }

    override fun onResume() {
        super.onResume()
        refreshTransactions()
        checkExpenseOverflowAlert()
    }

    private fun checkExpenseOverflowAlert() {
        val totalExpense = calculateTotalExpense()
        val budget = pref.getBudget()

        if (totalExpense > budget) {
            val builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Budget Exceeded!")
            builder.setMessage("Your expenses have exceeded your set budget. Please review your expenses.")
            builder.setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
        }
    }
}
