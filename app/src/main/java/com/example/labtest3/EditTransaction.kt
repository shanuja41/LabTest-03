package com.example.labtest3

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity
import com.example.labtest3.model.Transaction
import com.example.labtest3.utils.SharedPrefHelper

class EditTransaction : ComponentActivity() {

    private lateinit var etTitle: EditText
    private lateinit var etAmount: EditText
    private lateinit var etCategory: EditText
    private lateinit var rgType: RadioGroup
    private lateinit var rbIncome: RadioButton
    private lateinit var rbExpense: RadioButton
    private lateinit var btnSave: Button
    private lateinit var pref: SharedPrefHelper

    private var transaction: Transaction? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_transaction)

        etTitle = findViewById(R.id.etTitle)
        etAmount = findViewById(R.id.etAmount)
        etCategory = findViewById(R.id.etCategory)
        rgType = findViewById(R.id.rgType)
        rbIncome = findViewById(R.id.rbIncome)
        rbExpense = findViewById(R.id.rbExpense)
        btnSave = findViewById(R.id.save)

        pref = SharedPrefHelper(this)

        val transactionId = intent.getLongExtra("transaction_id", -1)
        if (transactionId != -1L) {
            val transactions = pref.getTransactions()
            transaction = transactions.find { it.id == transactionId }

            transaction?.let {
                etTitle.setText(it.title)
                etAmount.setText(it.amount.toString())
                etCategory.setText(it.category)
                if (it.type == "Income") {
                    rbIncome.isChecked = true
                } else {
                    rbExpense.isChecked = true
                }
            }
        }

        btnSave.setOnClickListener {
            saveChanges()
        }
    }

    private fun saveChanges() {
        transaction?.let {
            it.title = etTitle.text.toString()
            it.amount = etAmount.text.toString().toDouble()
            it.category = etCategory.text.toString()
            it.type = if (rbIncome.isChecked) "Income" else "Expense"

            pref.updateTransaction(it)

            Toast.makeText(this, "Transaction updated!", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, activity_dashboard::class.java))
            finish()
        }
    }
}
