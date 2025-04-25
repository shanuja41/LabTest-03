package com.example.labtest3.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.labtest3.R
import com.example.labtest3.model.Transaction

class TransactionAdapter(
    private val transactions: List<Transaction>,
    private val onEditClick: (Transaction) -> Unit,
    private val onDeleteClick: (Transaction) -> Unit
) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tv_title)
        val amount: TextView = view.findViewById(R.id.tv_amount)
        val category: TextView = view.findViewById(R.id.tv_category)
        val type: TextView = view.findViewById(R.id.tv_type)
        val editButton: Button = view.findViewById(R.id.btn_edit)
        val deleteButton: Button = view.findViewById(R.id.btn_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = transactions[position]

        // Set up the click listeners for edit and delete buttons
        holder.editButton.setOnClickListener {
            onEditClick(transaction)  // Pass the transaction to the callback
        }


        holder.deleteButton.setOnClickListener {
            onDeleteClick(transaction)
        }

        // Bind the data to the UI components
        holder.title.text = "Title: ${transaction.title}"
        holder.amount.text = "Amount: LKR %.2f".format(transaction.amount)
        holder.category.text = "Category: ${transaction.category}"
        holder.type.text = transaction.type

        // Color code based on transaction type (Income or Expense)
        if (transaction.type == "Income") {
            holder.amount.setTextColor(Color.parseColor("#2E7D32")) // Green for Income
            holder.type.setTextColor(Color.parseColor("#2E7D32"))
        } else {
            holder.amount.setTextColor(Color.parseColor("#C62828")) // Red for Expense
            holder.type.setTextColor(Color.parseColor("#C62828"))
        }
    }

    override fun getItemCount(): Int = transactions.size
}