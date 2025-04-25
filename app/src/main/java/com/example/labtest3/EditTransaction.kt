//package com.example.labtest3
//
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import android.widget.RadioButton
//import android.widget.RadioGroup
//import android.widget.Toast
//import androidx.activity.ComponentActivity
//import com.example.labtest3.model.Transaction
//import com.example.labtest3.utils.SharedPrefHelper
//
//class EditTransaction : ComponentActivity() {
//
//    private lateinit var etTitle: EditText
//    private lateinit var etAmount: EditText
//    private lateinit var etCategory: EditText
//    private lateinit var rgType: RadioGroup
//    private lateinit var rbIncome: RadioButton
//    private lateinit var rbExpense: RadioButton
//    private lateinit var btnSave: Button
//    private lateinit var pref: SharedPrefHelper
//    private var transaction: Transaction? = null  // Make this nullable
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_edit_transaction)
//
//        etTitle = findViewById(R.id.etEditTitle)
//        etAmount = findViewById(R.id.etEditAmount)
//        etCategory = findViewById(R.id.etEditCategory)
//        rgType = findViewById(R.id.rgEditType)
//        rbIncome = findViewById(R.id.rbEditIncome)
//        rbExpense = findViewById(R.id.rbEditExpense)
//        btnSave = findViewById(R.id.btnSaveEditedTransaction)
//
//        pref = SharedPrefHelper(this)
//
//        // Get the transaction ID passed from the dashboard
//        val transactionId = intent.getLongExtra("TRANSACTION_ID", -1L)
//
//        transaction = pref.getTransactionById(transactionId) // This can return null
//
//        if (transaction == null) {
//            Toast.makeText(this, "Transaction not found", Toast.LENGTH_SHORT).show()
//            finish()
//            return
//        }
//
//        // Populate the fields with the current data
//        etTitle.setText(transaction?.title)
//        etAmount.setText(transaction?.amount.toString())
//        etCategory.setText(transaction?.category)
//
//        if (transaction?.type.equals("income", ignoreCase = true)) {
//            rbIncome.isChecked = true
//        } else {
//            rbExpense.isChecked = true
//        }
//
//        btnSave.setOnClickListener {
//            val updatedTitle = etTitle.text.toString()
//            val updatedAmount = etAmount.text.toString().toDoubleOrNull()
//            val updatedCategory = etCategory.text.toString()
//            val updatedType = if (rbIncome.isChecked) "income" else "expense"
//
//            if (updatedTitle.isNotEmpty() && updatedAmount != null && updatedAmount >= 0) {
//                // Update the transaction data
//                transaction?.title = updatedTitle
//                transaction?.amount = updatedAmount
//                transaction?.category = updatedCategory
//                transaction?.type = updatedType
//
//                // Ensure the transaction is updated
//                if (transaction != null) {
//                    pref.updateTransaction(transaction!!)
//                }
//
//                // Notify the user and navigate back
//                Toast.makeText(this, "Transaction updated!", Toast.LENGTH_SHORT).show()
//                finish()
//            } else {
//                Toast.makeText(this, "Please enter valid data", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//}
