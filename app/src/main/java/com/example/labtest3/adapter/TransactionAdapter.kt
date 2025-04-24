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

class TransactionAdapter(private val list: List<Transaction>,
                         private val onEditClick: (Transaction) -> Unit,
                         private val onDeleteClick: (Transaction) -> Unit) :
    RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tv_title)
        val amount: TextView = view.findViewById(R.id.tv_amount)
        val category: TextView = view.findViewById(R.id.tv_category)
        val type: TextView = view.findViewById(R.id.tv_type)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = list[position]

        holder.itemView.findViewById<Button>(R.id.btn_edit).setOnClickListener {
            onEditClick(transaction)
        }
        holder.itemView.findViewById<Button>(R.id.btn_delete).setOnClickListener {
            onDeleteClick(transaction)
        }

        holder.title.text = "Title: ${transaction.title}"
        holder.amount.text = "Amount: LKR %.2f".format(transaction.amount)
        holder.category.text = "Category: ${transaction.category}"
        holder.type.text = transaction.type

        // Color code based on transaction type
        if (transaction.type == "Income") {
            holder.amount.setTextColor(Color.parseColor("#2E7D32")) // Green
            holder.type.setTextColor(Color.parseColor("#2E7D32"))
        } else {
            holder.amount.setTextColor(Color.parseColor("#C62828")) // Red
            holder.type.setTextColor(Color.parseColor("#C62828"))
        }



    }

    override fun getItemCount(): Int = list.size
}
