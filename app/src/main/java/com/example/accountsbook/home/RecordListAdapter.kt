package com.example.accountsbook.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.accountsbook.R
import com.example.accountsbook.database.RecordEntity
import com.example.accountsbook.util.color

class RecordListAdapter :
    ListAdapter<RecordEntity, RecordListAdapter.RecordViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<RecordEntity>() {
            override fun areItemsTheSame(oldItem: RecordEntity, newItem: RecordEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: RecordEntity, newItem: RecordEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        return RecordViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_home_record,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        val record = getItem(position)
        holder.bindView(record)
    }

    class RecordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val iconIv: ImageView = itemView.findViewById(R.id.iv_list_item_home_record_icon)
        private val categoryTv: TextView =
            itemView.findViewById(R.id.tv_list_item_home_record_category)
        private val descriptionTv: TextView =
            itemView.findViewById(R.id.tv_list_item_home_record_description)
        private val amountTv: TextView = itemView.findViewById(R.id.tv_list_item_home_record_amount)

        fun bindView(record: RecordEntity) {
            iconIv.setBackgroundResource(record.icon)
            categoryTv.text = record.category
            descriptionTv.text = record.description
            amountTv.text = record.amount.toString()
            amountTv.setTextColor(
                if (record.isIncome) R.color.colorPrimary.color() else R.color.black.color()
            )
        }
    }
}
