package com.example.accountsbook.home.recordForm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.accountsbook.R
import com.example.accountsbook.database.CategoryEntity

class CategoryListAdapter :
    ListAdapter<CategoryEntity, CategoryListAdapter.CategoryEntityViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<CategoryEntity>() {
            override fun areItemsTheSame(
                oldItem: CategoryEntity,
                newItem: CategoryEntity
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: CategoryEntity,
                newItem: CategoryEntity
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryEntityViewHolder {
        return CategoryEntityViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_category,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryEntityViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    class CategoryEntityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val iconIv: ImageView = itemView.findViewById(R.id.iv_list_item_category_icon)
        private val textTv: TextView = itemView.findViewById(R.id.tv_list_item_category_text)

        fun bindView(item: CategoryEntity) {
            iconIv.setBackgroundResource(item.icon)
            textTv.text = item.title
        }
    }
}
