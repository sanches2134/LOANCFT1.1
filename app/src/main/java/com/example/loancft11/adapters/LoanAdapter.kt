package com.example.loancft11.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.loancft11.R
import com.example.loancft11.models.LoanModel

class LoanAdapter(var items: List<LoanModel>, val callback: Callback) : RecyclerView.Adapter<LoanAdapter.MainHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = MainHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.loanrow,
            parent,
            false
        )
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        holder.bind(items[position])

    }

    inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val amount = itemView.findViewById<TextView>(R.id.amount)
        private val percent = itemView.findViewById<TextView>(R.id.percent)

        fun bind(item: LoanModel) {
            amount.text = item.firstName
            percent.text = item.lastName
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) callback.onItemClicked(items[adapterPosition])
            }
        }
    }

    interface Callback {
        fun onItemClicked(item: LoanModel)
    }

}