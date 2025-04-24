package com.example.labtest3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import com.example.labtest3.model.Transaction
import com.example.labtest3.utils.SharedPrefHelper

//import androidx.appcompat.app.AppCompatActivity

class AddExpenseActivity : ComponentActivity() {

    private lateinit var etExpenseTitle: EditText
    private lateinit var etExpenseAmount: EditText
    private lateinit var etExpenseCategory: EditText
    private lateinit var btnSaveExpense: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense) // make sure this matches your XML file

        // Initialize views
        etExpenseTitle = findViewById(R.id.et_expense_title)
        etExpenseAmount = findViewById(R.id.et_expense_amount)
        etExpenseCategory = findViewById(R.id.et_expense_category)
        btnSaveExpense = findViewById(R.id.btn_save_expense)

        // Set onClickListener
        btnSaveExpense.setOnClickListener {
            val title = etExpenseTitle.text.toString()
            val amountText = etExpenseAmount.text.toString()
            val category = etExpenseCategory.text.toString()

            if (title.isNotEmpty() && amountText.isNotEmpty() && category.isNotEmpty()) {
                val amount = amountText.toDouble()

                val transaction = Transaction(
                    title = title,
                    amount = amount,
                    category = category,
                    type = "Expense"
                )

                val pref = SharedPrefHelper(this)
                pref.saveTransaction(transaction)

                startActivity(Intent(this, activity_dashboard::class.java))
                finish()
            } else {
                // Optionally show error if any field is empty
                // Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
