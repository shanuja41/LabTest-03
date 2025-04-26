package com.example.labtest3

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
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
    private var transaction: Transaction? = null  // Make this nullable

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_transaction)

        val save = findViewById<TextView>(R.id.save)
        save.setOnClickListener {
            val intent = Intent(this, activity_dashboard::class.java)
            startActivity(intent)
            finish()
        }
    }
}
