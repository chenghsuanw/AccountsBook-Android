package com.example.accountsbook.home.recordForm.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.accountsbook.R
import com.example.accountsbook.home.recordForm.RecordFormAdapter
import com.example.accountsbook.home.recordForm.RecordFormItem

class ReceiptDelegate : RecordFormAdapter.TypedDelegate<RecordFormItem.Receipt> {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return RecordFormAdapter.ReceiptViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_record_form_receipt,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(item: RecordFormItem.Receipt, holder: RecyclerView.ViewHolder) {
        (holder as? RecordFormAdapter.ReceiptViewHolder)?.bindView(item)
    }
}
