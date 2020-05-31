package com.example.accountsbook.home.recordForm.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.accountsbook.R
import com.example.accountsbook.home.recordForm.RecordFormAdapter
import com.example.accountsbook.home.recordForm.RecordFormItem

class ConfirmDelegate(
    private val listener: RecordFormAdapter.ConfirmViewHolder.EventListener?
) : RecordFormAdapter.TypedDelegate<RecordFormItem.Confirm> {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return RecordFormAdapter.ConfirmViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_record_form_confirm,
                parent,
                false
            ), listener
        )
    }

    override fun onBindViewHolder(
        item: RecordFormItem.Confirm,
        holder: RecyclerView.ViewHolder
    ) {
        (holder as? RecordFormAdapter.ConfirmViewHolder)?.bindView(item)
    }
}
