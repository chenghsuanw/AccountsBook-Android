package com.example.accountsbook.home.recordForm.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.accountsbook.R
import com.example.accountsbook.home.recordForm.RecordFormAdapter
import com.example.accountsbook.home.recordForm.RecordFormItem

class CategoryDelegate(
    private val listener: RecordFormAdapter.EventListener?
) : RecordFormAdapter.TypedDelegate<RecordFormItem.Category> {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return RecordFormAdapter.CategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_record_form_category,
                parent,
                false
            ), listener
        )
    }

    override fun onBindViewHolder(item: RecordFormItem.Category, holder: RecyclerView.ViewHolder) {
        (holder as? RecordFormAdapter.CategoryViewHolder)?.bindView(item)
    }
}
