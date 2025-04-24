package com.example.labtest3

import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity
import com.example.labtest3.R

class activity_dashboard : ComponentActivity() {

    private lateinit var tvBudget: TextView
    private lateinit var etBudget: EditText
    private lateinit var btnSetBudget: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard) // Make sure your XML file name is correct

        // Linking Views
        tvBudget = findViewById(R.id.tv_budget)
        etBudget = findViewById(R.id.et_budget)
        btnSetBudget = findViewById(R.id.btn_set_budget)

        // SharedPreferences to store budget persistently
        val sharedPrefs = getSharedPreferences("finance_prefs", Context.MODE_PRIVATE)
        val savedBudget = sharedPrefs.getFloat("budget", 0f)
        tvBudget.text = "Budget: LKR %.2f".format(savedBudget)

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
    }
}
