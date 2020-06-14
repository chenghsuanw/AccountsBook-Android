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
import com.example.accountsbook.util.color

class CategoryListAdapter(
    private val listener: EventListener?
) : ListAdapter<CategoryEntity, CategoryListAdapter.CategoryEntityViewHolder>(diffCallback) {

    interface EventListener : CategoryEntityViewHolder.EventListener

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

    var focusedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryEntityViewHolder {
        return CategoryEntityViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_category,
                parent,
                false
            ), listener
        ) { clickedPosition ->
            focusedPosition = clickedPosition
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: CategoryEntityViewHolder, position: Int) {
        holder.itemView.isSelected = (focusedPosition == position)
        holder.bindView(getItem(position))
    }

    class CategoryEntityViewHolder(
        itemView: View,
        listener: EventListener?,
        onClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        interface EventListener {
            fun onCategoryChanged(category: CategoryEntity)
        }

        private lateinit var item: CategoryEntity
        private val iconIv: ImageView = itemView.findViewById(R.id.iv_list_item_category_icon)
        private val textTv: TextView = itemView.findViewById(R.id.tv_list_item_category_text)

        init {
            itemView.setOnClickListener {
                listener?.onCategoryChanged(item)
                onClicked.invoke(adapterPosition)
            }
        }

        fun bindView(item: CategoryEntity) {
            this.item = item
            iconIv.setBackgroundResource(item.icon)
            textTv.text = item.title
            textTv.setTextColor(
                if (itemView.isSelected) {
                    R.color.white.color()
                } else {
                    R.color.black.color()
                }
            )
        }
    }
}
