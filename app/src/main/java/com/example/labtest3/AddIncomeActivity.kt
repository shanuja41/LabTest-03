package com.example.labtest3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import com.example.labtest3.model.Transaction
import com.example.labtest3.utils.SharedPrefHelper

//import androidx.appcompat.app.AppCompatActivity

class AddIncomeActivity : ComponentActivity(){

    private lateinit var etIncomeTitle: EditText
    private lateinit var etIncomeAmount: EditText
    private lateinit var etIncomeCategory: EditText
    private lateinit var btnSaveIncome: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_income) // Make sure your XML is named this way

        // Initialize views
        etIncomeTitle = findViewById(R.id.et_income_title)
        etIncomeAmount = findViewById(R.id.et_income_amount)
        etIncomeCategory = findViewById(R.id.et_income_category)
        btnSaveIncome = findViewById(R.id.btn_save_income)

        // Set onClickListener
        btnSaveIncome.setOnClickListener {
            val title = etIncomeTitle.text.toString()
            val amountText = etIncomeAmount.text.toString()
            val category = etIncomeCategory.text.toString()

            if (title.isNotEmpty() && amountText.isNotEmpty() && category.isNotEmpty()) {
                val amount = amountText.toDouble()

                val transaction = Transaction(
                    title = title,
                    amount = amount,
                    category = category,
                    type = "Income"
                )

                val pref = SharedPrefHelper(this)
                pref.saveTransaction(transaction)

                startActivity(Intent(this, activity_dashboard::class.java))
                finish()
            } else {
                // Optional: Show error if fields are empty
                // Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
